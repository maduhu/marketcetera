package org.marketcetera.core.notifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.marketcetera.core.notifications.INotification.Severity;

/* $License$ */

/**
 * Tests {@link Notification}.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id: $
 * @since $Release$
 */
public class NotificationTest
{
    @Test
    public void staticConstructors()
        throws Exception
    {
        long beginTime = System.currentTimeMillis();
        Thread.sleep(100);
        // test low
        String subject = "subject_" + System.nanoTime();
        String body = "body_" + System.nanoTime();
        Class<?> originator = this.getClass();
        Notification low = Notification.low(subject,
                                            body,
                                            originator);
        assertNotNull(low);
        assertEquals(subject,
                     low.getSubject());
        assertEquals(body,
                     low.getBody());
        assertEquals(originator,
                     low.getOriginator());
        assertEquals(Severity.LOW,
                     low.getSeverity());
        assertNotNull(low.getDate());
        assertTrue(low.getDate().getTime() > beginTime);
        // test medium
        Thread.sleep(100);
        subject = "subject_" + System.nanoTime();
        body = "body_" + System.nanoTime();
        originator = Notification.class;
        Notification medium = Notification.medium(subject,
                                                  body,
                                                  originator);
        assertNotNull(medium);
        assertEquals(subject,
                     medium.getSubject());
        assertEquals(body,
                     medium.getBody());
        assertEquals(originator,
                     medium.getOriginator());
        assertEquals(Severity.MEDIUM,
                     medium.getSeverity());
        assertNotNull(medium.getDate());
        assertTrue(medium.getDate().getTime() > low.getDate().getTime());
        // test high
        Thread.sleep(100);
        subject = "subject_" + System.nanoTime();
        body = "body_" + System.nanoTime();
        originator = Notification.class;
        Notification high = Notification.high(subject,
                                              body,
                                              originator);
        assertNotNull(high);
        assertEquals(subject,
                     high.getSubject());
        assertEquals(body,
                     high.getBody());
        assertEquals(originator,
                     high.getOriginator());
        assertEquals(Severity.HIGH,
                     high.getSeverity());
        assertNotNull(high.getDate());
        assertTrue(high.getDate().getTime() > medium.getDate().getTime());
    }
}
