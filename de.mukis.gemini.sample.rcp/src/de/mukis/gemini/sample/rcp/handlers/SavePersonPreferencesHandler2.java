package de.mukis.gemini.sample.rcp.handlers;

import java.sql.SQLException;

import org.eclipse.e4.core.di.annotations.Execute;

import de.mukis.gemini.sample.model.Person;
import de.mukis.gemini.sample.rcp.dao.PersonDAO;

/**
 * This class injects a {@linkplain PersonDAO} each time the handler is
 * executed. Using this <br>
 * approach you can easily use eclipse preferences dynamically as the DAO is <br>
 * newly created each time and thus gets the current preferences injected.
 * 
 * @author muki
 * @see PersonDAO
 * 
 */
public class SavePersonPreferencesHandler2 {

    @Execute
    public void execute(PersonDAO dao) throws SQLException {
        dao.save(new Person("Mario", "Nintendo"));
    }

}
