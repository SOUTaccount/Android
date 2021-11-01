package first.example.bitsandpizzas;

public class Pizza {
    private String name;
    private int resourceId;
    public static final Pizza[] pizzas={
            new Pizza("Diavolo",R.drawable.diavolo),
            new Pizza("Delete",R.drawable.diavolo),
            new Pizza("I menya delete",R.drawable.diavolo),
            new Pizza("I menya delete2",R.drawable.diavolo),
            new Pizza("I menya delete3",R.drawable.diavolo),
            new Pizza("I menya delete4",R.drawable.diavolo),
            new Pizza("Funghi",R.drawable.funghi),
            new Pizza("NewPizza",R.drawable.funghi)
    };
    private Pizza(String name,int resourceId){
        this.name=name;
        this.resourceId=resourceId;
    }
    public String getName(){
        return name;
    }
    public int getResourceId(){
        return resourceId;
    }
}
