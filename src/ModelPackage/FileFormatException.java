package ModelPackage;

public class FileFormatException extends Exception {
    String error;

    public FileFormatException(String error){
        this.error = error;
    }


    public String getError(){
        return error;
    }

}
