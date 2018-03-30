package server.plagiarism.engine.parsetreebased;

import server.plagiarism.engine.parsetreebased.nodes.*;

/*
 * @author Rahul Verma [verma.rah@husky.neu.edu]
visitor to visit syntax tree
*/
public interface ASTVisitor {
        /*        * Visits Assignments*/
        public void visit(Assignment assn);
        /*
        * Visits  VarDeclaration Declaration*/
        public void visit(VarDeclaration decl);
        /*
        * Visits BinaryOperation*/
        public void visit(BinaryOperation bo);
        /*
        * Visits Unary Operation*/
        public void visit(UnaryOperation preFix);
        /*
        * Visits NumberExpression*/
        public void visit(NumberExpression numExp);
        /*
        * Visits StringExpression*/
        public void visit(StringExpression strExp);
        /*
        * Visits Sequence*/
       // public void visit(Sequence seq);
        /*
        * Visits VariableExpression*/
        public void visit(VariableExpression varExp);
        /*
        * Visits ClassDeclaration*/
        public void visit(ClassDeclaration cls);
        /*
        * Visits FunctionDeclaration*/
        public void visit(FunctionDeclaration strExp);
        /*
        * Visits FunctionCall*/
        public void visit(FunctionCall functionCall);
        /*
        * Visits If*/
        public void visit(If seq);
        /*
         * Visits While*/
        public void visit(While varExp);
        /* Visits For*/
        public void visit(For varExp);

        /* Visits Boolean*/
        public void visit(BooleanExpression booleanExpression);

        /* Visits BlockStatement*/

        public void visit(BlockStatement blockStatement);

        /* Visits ConditionalExpression*/

        public void visit(ConditionalExpression conditionalExpression);

        /* Visits ExpressionStatement*/

        public void visit(ExpressionStatement expressionStatement);
}
