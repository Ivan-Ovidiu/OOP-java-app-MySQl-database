package FoodGrabClasses;

public class Food {
        static Integer foodId;
        double grams;
        double price;
        String name;

 //Cosntructors

        public Food() {}
        public Food(String _name, double _grams, double _price) {
                name = _name;
                grams = _grams;
                price = _price;
        }
 //Getters
        public String getName(){return name;}
        public double getGrams(){return grams;}
        public double getPrice(){return price;}
        public Integer getFoodId(){return foodId;}

//Setters
      public void setFoodId(Integer _foodId){foodId = _foodId;}

}
