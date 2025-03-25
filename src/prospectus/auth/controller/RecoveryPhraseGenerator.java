package prospectus.auth.controller;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import main.dbConnector;

public class RecoveryPhraseGenerator {
    private dbConnector db2 = new dbConnector(); 

    private static final List<String> WORD_LIST = Arrays.asList(
        "apple", "banana", "cherry", "dog", "elephant", "flower", "grape", "house", "island", "jungle",
        "kangaroo", "lemon", "mountain", "notebook", "orange", "penguin", "queen", "rainbow", "sunflower", "tiger",
        "umbrella", "volcano", "waterfall", "xylophone", "yellow", "zebra"
    );

    public String generateUniqueRecoveryPhrase() {
        SecureRandom random = new SecureRandom();
        String recoveryPhrase;
        do {
            StringBuilder phrase = new StringBuilder();
            for (int i = 0; i < 12; i++) { // Generate a 12-word phrase
                phrase.append(WORD_LIST.get(random.nextInt(WORD_LIST.size()))).append(" ");
            }
            recoveryPhrase = phrase.toString().trim();
        } while (isRecoveryPhraseExists(recoveryPhrase)); 

        return recoveryPhrase;
    }


    public boolean isRecoveryPhraseExists(String phrase) {
        String query = "SELECT COUNT(*) FROM user WHERE recovery_phrase = ?";
        try (Connection conn = db2.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, phrase);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // if phrase already exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking recovery phrase: " + e.getMessage());
        }
        return false; // unique
    }
}
