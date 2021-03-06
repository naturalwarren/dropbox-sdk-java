package com.dropbox.core;

/**
 * Base class for API-specific exceptions raised by API v2 routes.
 */
public class DbxApiException extends DbxException
{
    /**
     * A human-readable message that can be displayed to the end user. Is {@code null} when unavailable.
     */
    public final LocalizedText userMessage;

    public DbxApiException(String requestId, LocalizedText userMessage, String message)
    {
        super(requestId, message);
        this.userMessage = userMessage;
    }

    public DbxApiException(String requestId, LocalizedText userMessage, String message, Throwable cause)
    {
        super(requestId, message, cause);
        this.userMessage = userMessage;
    }

    protected static String buildMessage(String routeName, LocalizedText userMessage)
    {
        return buildMessage(routeName, userMessage, null);
    }

    protected static String buildMessage(String routeName, LocalizedText userMessage, Object errorValue)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception in ").append(routeName);
        if (errorValue != null) {
            sb.append(": ").append(errorValue);
        }
        if (userMessage != null) {
            sb.append(" (user message: ").append(userMessage).append(")");
        }
        return sb.toString();
    }
}
