package com.gps.beans;

import com.gps.helpers.JSFHelper;
import com.gps.util.JsfUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "fileUploadManagedBean")
public class FileUploadManagedBean {

    private static final Logger logger = Logger.getLogger(FileUploadManagedBean.class);
    
    private Login login = (Login) JSFHelper.getManagedBean("login");
    private UtilisateurManagedBean utilisateurManagedBean = (UtilisateurManagedBean) JSFHelper.getManagedBean("utilisateurManagedBean");
    private PageUri page = (PageUri) JSFHelper.getManagedBean("page");

    public void fileUpload(FileUploadEvent file) {

        if (file != null) {

            ServletContext ctx = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getContext("/static");
            String path = ctx.getRealPath("") + "/images/avatar";
            try {
                String avatar = uploadFile(file.getFile(), path, login.getConnectedUser().getAvatar());
                login.getConnectedUser().setAvatar(avatar);
                login.setUrlAvatar(page.requestPath() + "/static/images/avatar/" + avatar);
                utilisateurManagedBean.update(login.getConnectedUser());
            } catch (FileNotFoundException ex) {
                logger.error(ex);
            }
        }
    }

    public String uploadFile(UploadedFile uploadedFile, String path, String newFileName) throws FileNotFoundException {

        if (uploadedFile == null || uploadedFile.getSize() == 0) {
            return null;
        }
        String ext = uploadedFile.getFileName();
        int x = ext.lastIndexOf('.');
        if (x != -1) {
            ext = ext.substring(x + 0);
            ext = ext.toLowerCase();
        }
        if (ext == null || (!ext.equals(".jpg") && !ext.equals(".jpeg") && !ext.equals(".png") && !ext.equals(".gif") && !ext.equals(".bmp"))) {
            JsfUtil.addErrorMessage("Type de fichier non supporté !!!");
            return null;
        }

        File file = null;
        OutputStream output = null;

        try {
            String suffix = "avatar_id" + login.getConnectedUser().getIdUtilisateur() + "_";
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();

            for (File f : listOfFiles) {
                if (f.getName().contains(suffix)) {
                    f.delete();
                    break;
                }
            }
            file = File.createTempFile(suffix, ext, new File(path));
            output = new FileOutputStream(file);
            IOUtils.copy(uploadedFile.getInputstream(), output);


        } catch (IOException e) {
            if (file != null) {
                file.delete();
            }
            JsfUtil.addErrorMessage(e, "Echec du chargement de l'image !!!");
        } finally {
            IOUtils.closeQuietly(output);
        }
        return file.getName();
    }
}
