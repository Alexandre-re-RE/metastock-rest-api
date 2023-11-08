package fr.cda.metastock.filter;

import java.io.IOException;

import org.wildfly.security.http.oidc.AccessToken;
import org.wildfly.security.http.oidc.OidcSecurityContext;

import fr.cda.metastock.model.Account;
import fr.cda.metastock.model.Account.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.transaction.Transactional;

@WebFilter(filterName = "OidcUser", urlPatterns = "/rest/*")
@Transactional
public class OidcUserWebFilter implements Filter {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        OidcSecurityContext securityContext = this.getOidcSecurityContext(request);

        if(securityContext != null) {
            Account account = this.getCurrentUser(securityContext);
            request.setAttribute("user", account);
        }

        chain.doFilter(request, response);

    }

    private Account getCurrentUser(OidcSecurityContext securityContext) {

        AccessToken token = securityContext.getToken();
        String id = token.getSubject();

        Account account = this.em.find(Account.class, id);

        if(account == null) {
            account = new Account();

            account.setId(id);
            account.setFirstname(token.getGivenName());
            account.setLastname(token.getFamilyName());
            account.setArchive(false);
            
            if(token.getRealmAccessClaim().getRoles().contains("logistician")) {
                account.setRole(Role.LOGISTICIAN);
            } else {
                account.setRole(Role.WAREHOUSEMAN);
            }

            this.em.persist(account);
        }

        return account;
    }

    private OidcSecurityContext getOidcSecurityContext(ServletRequest request) {
        return (OidcSecurityContext) request.getAttribute(OidcSecurityContext.class.getName());
    }
}
