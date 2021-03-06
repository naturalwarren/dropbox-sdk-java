/* DO NOT EDIT */
/* This file was generated by Babel */

package com.dropbox.core.v2;

import com.dropbox.core.DbxHost;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.HttpRequestor;

/**
 * Use this class to make remote calls to the Dropbox API team endpoints.  Team
 * endpoints expose actions you can perform on or for a Dropbox team.  You'll
 * need a team access token first, normally acquired by directing a Dropbox
 * Business team administrator through the auth flow using {@link
 * com.dropbox.core.DbxWebAuth}.
 *
 * <p> Team clients can access user endpoints by using the {@link #asMember}
 * method.  This allows team clients to perform actions as a particular team
 * member. </p>
 *
 * <p> This class has no mutable state, so it's thread safe as long as you pass
 * in a thread safe {@link HttpRequestor} implementation. </p>
 */
public final class DbxTeamClientV2 {
    private final DbxRawClientV2 rawClient;
    public final DbxTeam team;

    /**
     * Creates a client that uses the given OAuth 2 access token as
     * authorization when performing requests against the default Dropbox hosts.
     *
     * @param requestConfig  Default attributes to use for each request
     * @param accessToken  OAuth 2 access token (that you got from Dropbox) that
     *     gives your app the ability to make Dropbox API calls. Typically
     *     acquired through {@link com.dropbox.core.DbxWebAuth}
     */
    public DbxTeamClientV2(DbxRequestConfig requestConfig, String accessToken) {
        this(requestConfig, accessToken, DbxHost.Default);
    }

    /**
     * Same as {@link #DbxTeamClientV2(DbxRequestConfig, String)} except you can
     * also set the hostnames of the Dropbox API servers. This is used in
     * testing. You don't normally need to call this.
     *
     * @param requestConfig  Default attributes to use for each request
     * @param accessToken  OAuth 2 access token (that you got from Dropbox) that
     *     gives your app the ability to make Dropbox API calls. Typically
     *     acquired through {@link com.dropbox.core.DbxWebAuth}
     * @param host  Dropbox hosts to send requests to (used for mocking and
     *     testing)
     */
    public DbxTeamClientV2(DbxRequestConfig requestConfig, String accessToken, DbxHost host) {
        this(new DbxRawClientV2(requestConfig, accessToken, host));
    }

    /**
     * For internal use only.
     *
     * @param requestConfig  Default attributes to use for each request
     * @param accessToken  OAuth 2 access token (that you got from Dropbox) that
     *     gives your app the ability to make Dropbox API calls. Typically
     *     acquired through {@link com.dropbox.core.DbxWebAuth}
     * @param host  Dropbox hosts to send requests to (used for mocking and
     *     testing)
     */
    DbxTeamClientV2(DbxRawClientV2 rawClient) {
        this.rawClient = rawClient;
        this.team = new DbxTeam(rawClient);
    }

    /**
     * Returns a {@link DbxClientV2} that performs requests against Dropbox API
     * user endpoints as the given team member.
     *
     * <p> This method performs no validation of the team member ID. </p>
     *
     * @param memberId  Team member ID of member in this client's team, never
     *     {@code null}.
     *
     * @return Dropbox client that issues requests to user endpoints as the
     *     given team member
     *
     * @throws IllegalArgumentException  If {@code memberId} is {@code null}
     */

    public DbxClientV2 asMember(String memberId) {
        if (memberId == null) throw new IllegalArgumentException("'memberId' should not be null");
        return new DbxClientV2(new DbxTeamRawClientV2(rawClient, memberId));
    }
    /**
     * {@link DbxRawClientV2} raw client that adds select-user header to all
     * requests. Used to perform requests as a particular team member.
     */
    private static final class DbxTeamRawClientV2 extends DbxRawClientV2 {
        private final String memberId;

        private DbxTeamRawClientV2(DbxRawClientV2 underlying, String memberId) {
            super(underlying);
            this.memberId = memberId;
        }

        @Override
        protected void addAuthHeaders(java.util.List<HttpRequestor.Header> headers) {
            super.addAuthHeaders(headers);
            com.dropbox.core.DbxRequestUtil.addSelectUserHeader(headers, memberId);
        }
    }
}
