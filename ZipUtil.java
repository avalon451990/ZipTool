import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created on 16/6/28.
 */
public class ZipUtil {

    /*
    * @param zipFilePath name of zip file
    * @param outPathString path to be unZIP
    * */
    public static void UnZipFile(String zipFilePath, String outPutPath) throws Exception{
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        UnZipFile(zipInputStream, outPutPath);
    }

    /*
    * @param inZip ZipInputStream of zip file
    * @param outPathString path to be unZIP
    * */
    public static void UnZipFile(ZipInputStream inZip, String outPutPath) throws Exception{
        File file = new File(outPutPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null){
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()){
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPutPath + File.separator + szName);
                folder.mkdirs();
            }else{
                file = new File(outPutPath + File.separator + szName);
                file.createNewFile();
                OutputStream outStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int byteread = 0;
                while ((byteread = inZip.read(buffer)) != -1){
                    outStream.write(buffer, 0, byteread);
                }
                outStream.flush();
                outStream.close();
            }
        }
        inZip.close();
    }

}
