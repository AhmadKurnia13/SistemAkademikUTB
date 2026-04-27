package Akademik;

public class Session {
    private static String idUser;
    private static String level;

    public static String getIdUser() {
        return idUser;
    }

    public static void setIdUser(String idUser) {
        Session.idUser = idUser;
    }

    public static String getLevel() {
        return level;
    }

    public static void setLevel(String level) {
        Session.level = level;
    }
    
    public static void clear() {
        idUser = null;
        level = null;
    }
}
