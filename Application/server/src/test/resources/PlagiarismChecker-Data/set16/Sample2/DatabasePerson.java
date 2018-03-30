package File2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.sql.DataSource;

public class DatabasePerson implements PersonDao {

  private final DataSource ds;

  /**
   * Creates an instance of {@link DatabasePerson} which uses provided <code>ds</code>
   * to store and retrieve c information.
   * 
   * @param ds a non-null ds.
   */
  public DatabasePerson(DataSource ds) {
    this.ds = ds;
  }

  /**
   * @return a lazily populated stream of customers. Note the stream returned must be closed to 
   *     free all the acquired resources. The stream keeps an open con to the database till
   *     it is complete or is closed manually.
   */
  public Stream<Person> getAll() throws Exception {

    Connection con;
    try {
      con = getConnection();
      PreparedStatement st = con.prepareStatement("SELECT * FROM CUSTOMERS"); // NOSONAR
      ResultSet rs = st.executeQuery(); // NOSONAR
      return StreamSupport.stream(new Spliterators.AbstractSpliterator<Person>(Long.MAX_VALUE, 
          Spliterator.ORDERED) {

        public boolean tryAdvance(Consumer<? super Person> action) {
          try {
            if (!rs.next()) {
              return false;
            }
            action.accept(createCustomer(rs));
            return true;
          } catch (Exception e) {
            throw new RuntimeException(e); // NOSONAR
          }
        }
      }, false).onClose(() -> mutedClose(con, st, rs));
    } catch (Exception e) {
      throw new Exception(e.getMessage(), e);
    }
  }

  private Connection getConnection() throws Exception {
    return ds.getConnection();
  }

  private void mutedClose(Connection con, PreparedStatement st, ResultSet rs) {
    try {
      rs.close();
      st.close();
      con.close();
    } catch (Exception e) {
      System.out.println("Exception thrown " + e.getMessage());
    }
  }

  private Person createCustomer(ResultSet rs) throws Exception {
    return new Person(rs.getInt("ID"), 
        rs.getString("FNAME"), 
        rs.getString("LNAME"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Person> getById(int id) throws Exception {

    ResultSet rs = null;

    try (Connection con = getConnection();
        PreparedStatement st = 
            con.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID = ?")) {

      st.setInt(1, id);
      rs = st.executeQuery();
      if (rs.next()) {
        return Optional.of(createCustomer(rs));
      } else {
        return Optional.empty();
      }
    } catch (Exception ex) {
      throw new Exception(ex.getMessage(), ex);
    } finally {
      if (rs != null) {
        rs.close();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean newObject(Person c) throws Exception {
    if (getById(c.getIdentifier()).isPresent()) {
      return false;
    }

    try (Connection con = getConnection();
        PreparedStatement st = 
            con.prepareStatement("INSERT INTO CUSTOMERS VALUES (?,?,?)")) {
      st.setInt(1, c.getIdentifier());
      st.setString(2, c.getfName());
      st.setString(3, c.getlName());
      st.execute();
      return true;
    } catch (Exception ex) {
      throw new Exception(ex.getMessage(), ex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean changeObject(Person c) throws Exception {
    try (Connection con = getConnection();
        PreparedStatement st = 
            con.prepareStatement("UPDATE CUSTOMERS SET FNAME = ?, LNAME = ? WHERE ID = ?")) {
      st.setString(1, c.getfName());
      st.setString(2, c.getlName());
      st.setInt(3, c.getIdentifier());
      return st.executeUpdate() > 0;
    } catch (Exception ex) {
      throw new Exception(ex.getMessage(), ex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeObject(Person c) throws Exception {
    try (Connection con = getConnection();
        PreparedStatement st = 
            con.prepareStatement("DELETE FROM CUSTOMERS WHERE ID = ?")) {
      st.setInt(1, c.getIdentifier());
      return st.executeUpdate() > 0;
    } catch (Exception e) {
      throw new Exception(e.getMessage(), e);
    }
  }
}
