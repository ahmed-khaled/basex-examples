package org.basex.examples.server;
test
import org.basex.*;
import org.basex.core.*;
import org.basex.core.cmd.*;
import org.basex.server.*;

/**
 * This class demonstrates database access via the client/server architecture.
 *
 * @author BaseX Team 2005-12, BSD License
 */
public final class ServerCommands {
  /** Session reference. */
  static ClientSession session;

  /**
   * Runs the example code.
   * @param args (ignored) command-line arguments
   * @throws Exception exception
   */
  public static void main(final String[] args) throws Exception {

    System.out.println("=== ServerCommands ===");

    // ------------------------------------------------------------------------
    // Start server on default port 1984
    BaseXServer server = new BaseXServer();

    // ------------------------------------------------------------------------
    // Create a client session with host name, port, user name and password
    System.out.println("\n* Create a client session.");

    session = new ClientSession("localhost", 1984, "admin", "admin");

    // ------------------------------------------------------------------------
    // Create a database
    System.out.println("\n* Create a database.");

    session.execute(new CreateDB("input", "src/main/resources/xml/input.xml"));

    // ------------------------------------------------------------------------
    // Run a query
    System.out.println("\n* Run a query:");

    System.out.println(session.query("//li").execute());

    // ------------------------------------------------------------------------
    // Faster version: specify an output stream and run a query
    System.out.println("\n* Run a query (faster):");

    session.setOutputStream(System.out);
    session.query("//li").execute();
    System.out.println();

    // Reset output stream
    session.setOutputStream(null);

    // ------------------------------------------------------------------------
    // Run a query
    System.out.println("\n\n* Run a buggy query: ");

    try {
      session.execute(new XQuery("///"));
    } catch(final BaseXException ex) {
      System.out.println(ex.getMessage());
    }

    // ------------------------------------------------------------------------
    // Drop the database
    System.out.println("\n* Close and drop the database.");

    session.execute(new DropDB("input"));

    // ------------------------------------------------------------------------
    // Close the client session
    System.out.println("\n* Close the client session.");

    session.close();

    // ------------------------------------------------------------------------
    // Stop the server
    System.out.println("\n* Stop the server.");

    server.stop();
  }
}
