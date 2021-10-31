package first.example.workout;

public class Workout {
    private String name;
    private String description;
    private Workout (String name, String description){
        this.name=name;
        this.description=description;
    }
    public static final Workout[] workouts = {
            new Workout("The limb loosener","5 prised\n10 otjim\n15 podtyagivanya"),
            new Workout("Core agony","55 prised\n101 otjim\n152 podtyagivanya"),
            new Workout("The wimp special","5 wtanga\n10 otjim\n15 bassein"),
            new Workout("Strength and length","5km beg\n10km plavat\n15km letat")

    };
public String getName(){
    return name;
}
public String getDescription(){
    return description;
}
public String toString(){
    return this.name;
}
}
