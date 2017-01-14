package nl.lakedigital.djfc.client;

import nl.lakedigital.djfc.client.oga.TelefonieBestandClient;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

public class TelefonieBestandClientTest {
    @Test
    public void testGetRecordingsAndVoicemails() throws Exception {
        TelefonieBestandClient telefonieBestandClient = PowerMock.createPartialMock(TelefonieBestandClient.class, "uitvoerenGetString");

        PowerMock.expectPrivate(telefonieBestandClient, "uitvoerenGetString", "/rest/telefonie/recordings?telefoonnummers=1").andReturn("{\"recordings\":[\"rg-8001-0614165929-20170102-115841-1483354721.74.wav\",\"out-0591377338-2912-20170102-185025-1483379425.187.wav\"]}");

        replay(telefonieBestandClient);

        Map<String, List<String>> result = telefonieBestandClient.getRecordingsAndVoicemails(newArrayList("1"));

        assertThat(result.get("recordings").size(), is(2));

        verify(telefonieBestandClient);
    }
}