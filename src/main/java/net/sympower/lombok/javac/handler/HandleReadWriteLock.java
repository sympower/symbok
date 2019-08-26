package net.sympower.lombok.javac.handler;

import static lombok.javac.handlers.JavacHandlerUtil.*;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Name;
import lombok.core.AST.Kind;
import lombok.core.AnnotationValues;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;
import lombok.javac.handlers.JavacHandlerUtil.MemberExistsResult;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import net.sympower.lombok.ConfigurationKeys;
import net.sympower.lombok.ReadLock;
import net.sympower.lombok.WriteLock;
import org.kohsuke.MetaInfServices;

public class HandleReadWriteLock {

  private static final String DEFAULT_LOCK_FIELD_NAME = "$readWriteLock";

  @MetaInfServices(JavacAnnotationHandler.class)
  public static class HandleReadLock extends JavacAnnotationHandler<ReadLock> {

    private static final String LOCK_CLASS_NAME = "java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock";
    private static final String LOCK_METHOD = "readLock";

    @Override
    public void handle(
        final AnnotationValues<ReadLock> annotation,
        final JCAnnotation ast,
        final JavacNode annotationNode
    ) {
      deleteAnnotationIfNeccessary(annotationNode, ReadLock.class);
      handleReadWriteLock(
          annotation.getInstance().value(),
          ast,
          annotationNode,
          ReadLock.class,
          LOCK_CLASS_NAME,
          LOCK_METHOD
      );
    }
  }

  @MetaInfServices(JavacAnnotationHandler.class)
  public static class HandleWriteLock extends JavacAnnotationHandler<WriteLock> {

    private static final String LOCK_CLASS_NAME = "java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock";
    private static final String LOCK_METHOD = "writeLock";

    @Override
    public void handle(
        final AnnotationValues<WriteLock> annotation,
        final JCAnnotation ast,
        final JavacNode annotationNode
    ) {
      deleteAnnotationIfNeccessary(annotationNode, WriteLock.class);
      handleReadWriteLock(
          annotation.getInstance().value(),
          ast,
          annotationNode,
          WriteLock.class,
          LOCK_CLASS_NAME,
          LOCK_METHOD
      );
    }
  }

  private static void handleReadWriteLock(
      String lockFieldName,
      JCAnnotation ast,
      JavacNode annotationNode,
      Class annotationType,
      String lockClass,
      String lockMethod
  ) {
    JavacNode methodNode = annotationNode.up();

    if (methodNode == null || methodNode.getKind() != Kind.METHOD || !(methodNode.get() instanceof JCMethodDecl)) {
      annotationNode.addError("@" + annotationType.getName() + " is legal only on methods.");
      return;
    }

    JCMethodDecl method = (JCMethodDecl) methodNode.get();

    if ((method.mods.flags & Flags.ABSTRACT) != 0) {
      annotationNode.addError("@" + annotationType.getName() + " is legal only on concrete methods.");
      return;
    }

    if (method.body == null) {
      return;
    }

    JavacTreeMaker maker = methodNode.getTreeMaker().at(ast.pos);
    Context context = methodNode.getContext();

    if (lockFieldName == null || lockFieldName.isEmpty()) {
      lockFieldName = annotationNode.getAst().readConfiguration(ConfigurationKeys.READ_WRITE_LOCK_DEFAULT_FIELD_NAME);
      if (lockFieldName == null || lockFieldName.isEmpty()) {
        lockFieldName = DEFAULT_LOCK_FIELD_NAME;
      }
    }

    if (fieldExists(lockFieldName, methodNode) == MemberExistsResult.NOT_EXISTS) {
      JCExpression lockType = genTypeRef(methodNode, "java.util.concurrent.locks.ReentrantReadWriteLock");
      JCExpression newInstance = maker.NewClass(null, List.nil(), lockType, List.nil(), null);
      JCVariableDecl fieldDecl = recursiveSetGeneratedBy(maker.VarDef(
          maker.Modifiers(Flags.PRIVATE | Flags.FINAL),
          methodNode.toName(lockFieldName), lockType, newInstance
      ), ast, context);
      injectFieldAndMarkGenerated(methodNode.up(), fieldDecl);
    }

    // ReentrantReadWriteLock.ReadLock/WriteLock $lock = this.anotherLock.readLock()/writeLock();
    JCExpression lockNode = maker.Select(maker.Ident(methodNode.toName("this")), methodNode.toName(lockFieldName));
    Name lockMethodName = methodNode.toName(lockMethod);
    JCExpression readLockNode = maker.Apply(List.nil(), maker.Select(lockNode, lockMethodName), List.nil());
    JCExpression readLockType = genTypeRef(methodNode, lockClass);
    Name lockVariableName = methodNode.toName("$lock");
    JCStatement readLockVariable = maker.VarDef(maker.Modifiers(0), lockVariableName, readLockType, readLockNode);

    // $lock.lock();
    JCExpression readLockLockNode = maker.Select(maker.Ident(lockVariableName), methodNode.toName("lock"));
    JCStatement readLockLockStatement = maker.Exec(maker.Apply(List.nil(), readLockLockNode, List.nil()));

    // $lock.unlock();
    JCExpression readLockUnlockNode = maker.Select(maker.Ident(lockVariableName), methodNode.toName("unlock"));
    List<JCStatement> readLockUnlockStatements = List.of(maker.Exec(
        maker.Apply(List.nil(), readLockUnlockNode, List.nil())));

    // compose everything together with try/finally block
    JCTree.JCBlock finalizer = recursiveSetGeneratedBy(maker.Block(0, readLockUnlockStatements), ast, context);
    method.body = setGeneratedBy(maker.Block(
        0,
        List.of(
            readLockVariable,
            readLockLockStatement,
            setGeneratedBy(maker.Try(method.body, List.nil(), finalizer), ast, context)
        )
    ), ast, context);

    methodNode.rebuild();
  }
}
