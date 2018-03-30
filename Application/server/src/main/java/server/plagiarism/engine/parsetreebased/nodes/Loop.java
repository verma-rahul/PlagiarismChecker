package server.plagiarism.engine.parsetreebased.nodes;

/*
  * @author Rahul Verma [verma.rah@husky.neu.edu]
  */
public abstract class Loop extends Statement {

    public Loop(Expression test,Statement body, int id) {
        this.test = test;
        this.body = body;
        this.id = id;
    }


    /**
     * Returns unique identifier for each node.
     */
    @Override
    public int getId() {
        return id;
    }

    public Expression getTest() {
        return test;
    }

    public Statement getBody() {
        return body;
    }

    private Expression test;
    private Statement body;
    private int id;
}
