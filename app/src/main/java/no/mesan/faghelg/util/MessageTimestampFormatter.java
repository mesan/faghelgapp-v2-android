package no.mesan.faghelg.util;

import org.joda.time.DateTime;

import no.mesan.faghelg.model.Message;

public class MessageTimestampFormatter {

    public static String formatTimestamp(Message message) {
        DateTime midnightToday = DateTime.now().withTimeAtStartOfDay();
        DateTime messageTimestamp = message.getTimestamp();
        if(messageTimestamp.isBefore(midnightToday)) {
            return message.getTimestamp().toString("EEE, dd MMM yyyy HH:mm");
        } else {
            return message.getTimestamp().toString("'kl.' HH:mm");
        }
    }
}
