package service.impl;

import dao.VideoDao;
import dao.impl.VideoDaoImpl;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.GenericService;
import service.InterfaceService;

import java.util.List;

public class VideoServiceImpl extends GenericService<Video> implements InterfaceService {

    private VideoDao videoDao;

    public VideoServiceImpl(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.videoDao = new VideoDaoImpl();
    }

    @Override
    public void loadListToPage() throws Exception {
        List<Video> videos = videoDao.paginate(super.getPageNumber(), super.PAGE_SIZE);
        String managePage = "/views/user/index.jsp";
        setupPagination(videoDao.count());
        request.setAttribute("videos", videos);
        message = message == null ? "" : message;
        super.setMessage(messageType, message);
        super.forward(managePage);
    }

    @Override
    public void addEntityFormPage() throws Exception {

    }

    @Override
    public void editEntityFormPage() throws Exception {

    }

    @Override
    public void deleteEntityFromPage() throws Exception {

    }

    @Override
    public void loadEditFormPage() throws Exception {

    }

    @Override
    public void loadCreateFormPage() throws Exception {

    }

    public void showDetails() throws Exception {
        String detailVideoPage = "/views/user/video-detail.jsp";
        String href = request.getParameter("id");
        Video video = videoDao.findByHref(href);
        super.setObject(video);
        super.forward(detailVideoPage);
    }
}
