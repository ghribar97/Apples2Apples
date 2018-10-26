import java.util.ArrayList;
import java.util.Arrays;

class neki1 extends abstractClass {
    @Override
    public String getString() {
        return null;
    }
}

public class neki {
    public static void main(String[] args) {
        abstractClass ac = new abstractClass() {
            @Override
            public String getString() {
                return null;
            }
        };

        SingletonClass obj = SingletonClass.getInstance();
        obj.addPlayer("lolek");
        System.out.println(Arrays.toString(obj.getList().toArray()));
        test nk = new test();
        nk.exec();
    }
}

class test {
    public void exec() {
        SingletonClass obj = SingletonClass.getInstance();
        obj.addPlayer("lolek");
        System.out.println(Arrays.toString(obj.getList().toArray()));
    }
}

class SingletonClass {
    private static SingletonClass instance;

    private final int port = 3003;
    private ArrayList<String> players;

    private SingletonClass() {
        players = new ArrayList<String>();
    }

    public static SingletonClass getInstance() {
        if (instance == null)
            instance = new SingletonClass();
        return instance;
    }

    public void addPlayer(String name) {
        this.players.add(name);
    }

    public ArrayList<String> getList() {
        return players;
    }


}


