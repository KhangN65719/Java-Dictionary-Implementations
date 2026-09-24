public class NullKeyException extends Exception{
    @Override
    public String getMessage(){
        return "Key cannot be null";
    }
}


