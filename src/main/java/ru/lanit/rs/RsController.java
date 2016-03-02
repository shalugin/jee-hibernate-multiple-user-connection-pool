package ru.lanit.rs;

import lombok.extern.slf4j.Slf4j;
import ru.lanit.MyRepo;
import ru.lanit.RefReputationSource;
import ru.lanit.RequestData;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("test")
@Slf4j
public class RsController extends HttpServlet {

    @EJB
    private MyRepo myRepo;
    @Inject
    private RequestData requestData;

    @GET
    public String getIt() {
        long l = System.currentTimeMillis();
        l = l % 500;
        requestData.setLogin("u" + l);
        requestData.setPassword("u" + l);
        log.info("Login for database: {}", requestData.getLogin());

        RefReputationSource data = myRepo.getData();
        return data.getName();
    }
}