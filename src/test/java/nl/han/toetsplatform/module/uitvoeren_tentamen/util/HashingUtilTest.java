package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HashingUtilTest {

    @Test
    public void generateHashTestWithShortString() throws IOException {
        //Arrange
        String inputForHash = "WASSAWASSABITCONNEEEEEECT";
        int seedForHash = 0x9747b28c;
        //Act
        String hash = HashingUtil.generateHash(inputForHash, seedForHash);
        //Assert
        assertEquals("-1096113272",hash);
    }

    @Test
    public void generateHashTestWithJsonLikeString() throws IOException {
        //Arrange
        String inputForHash = "{"
                + "    \"geodata\": ["
                + "        {"
                + "                \"id\": \"1\","
                + "                \"name\": \"Julie Sherman\","
                + "                \"gender\" : \"female\","
                + "                \"latitude\" : \"37.33774833333334\","
                + "                \"longitude\" : \"-121.88670166666667\""
                + "                }"
                + "        },"
                + "        {"
                + "                \"id\": \"2\","
                + "                \"name\": \"Johnny Depp\","
                + "                \"gender\" : \"male\","
                + "                \"latitude\" : \"37.336453\","
                + "                \"longitude\" : \"-121.884985\""
                + "                }"
                + "        }"
                + "    ]"
                + "}";
        int seedForHash = 0x9747b28c;
        //Act
        String hash = HashingUtil.generateHash(inputForHash, seedForHash);
        //Assert
        assertEquals("-1057168514",hash);
    }
}
