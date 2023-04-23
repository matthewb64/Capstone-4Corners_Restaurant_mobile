package com.example.a4corners;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {
    Button placedOrders, cart;
    RecyclerView specialRev, mealsRev, drinksRev;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        placedOrders = view.findViewById(R.id.placedOrders);
        cart = view.findViewById(R.id.cart);
        specialRev = view.findViewById(R.id.specialRev);
        mealsRev = view.findViewById(R.id.mealsRev);
        drinksRev = view.findViewById(R.id.drinksRev);

        specialRev.setLayoutManager(new LinearLayoutManager(getContext()));
        mealsRev.setLayoutManager(new LinearLayoutManager(getContext()));
        drinksRev.setLayoutManager(new LinearLayoutManager(getContext()));

        placedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ViewOrdersActivity.class));
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });

        viewData();

        return view;
    }

    public void viewData(){
        ArrayList<GetterSetterSpecial> alSpecial = new ArrayList<>();
        ArrayList<GetterSetterSpecial> alMeals = new ArrayList<>();
        ArrayList<GetterSetterSpecial> alDrinks = new ArrayList<>();

        alSpecial.add(new GetterSetterSpecial("Special1", "Indian Butter Chicken", "butter", "67", "Butter chicken, traditionally known as murgh makhani, is an Indian dish originating in New Delhi."));
        alSpecial.add(new GetterSetterSpecial("Special2", "Injela", "injela", "100", "Injera is a sour fermented pancake-like flatbread with a slightly spongy texture, traditionally made of teff flour."));
        alSpecial.add(new GetterSetterSpecial("Special3", "Irish Colcannon Potatoes", "irish", "50", "Colcannon is simply a traditional Irish dish of mashed potatoes with cabbage or kale."));
        alSpecial.add(new GetterSetterSpecial("Special4", "Italian Margherita Pizza", "pizza", "120", "This margherita pizza recipe tastes like an artisan pie from Italy! It's the ideal meld of zingy tomato sauce, gooey cheese and chewy crust."));
        alSpecial.add(new GetterSetterSpecial("Special5", "Japanese Sushi Rolls", "shushi", "80", "Makizushi is made by wrapping up fillings in rice and nori seaweed."));
        alSpecial.add(new GetterSetterSpecial("Special6", "Mughlai Style Chicken", "chicken", "150", "Key Ingredients: Chicken, Almond, Cream, Yoghurt, Ginger , Garlic, Onion, Green Chilli Spices, Oil, Coriander Leaves, Salt."));
        alSpecial.add(new GetterSetterSpecial("Special7", "Paella Valenciana", "paella", "73", "Valencia paella is a rice centered dish meaning the rice and the preparation of the rice is the most important part of the recipe."));
        alSpecial.add(new GetterSetterSpecial("Special8", "Thai Pad Thai", "pad", "88", "Pad thai, phat thai, or phad thai, is a stir-fried rice noodle dish commonly served as a street food in Thailand as part of the country's cuisine."));

        alMeals.add(new GetterSetterSpecial("Meal1", "Broccoli Chicken Casserole", "brocolli", "123", "Preheat the oven to 350 degrees F. Grease a 9-by-13-by-2-inch baking dish with nonstick cooking spray or butter."));
        alMeals.add(new GetterSetterSpecial("Meal2", "Chicken and Gravy", "gravy", "432", "This chicken and gravy recipe has tender pan fried chicken breasts smothered in a rich gravy."));
        alMeals.add(new GetterSetterSpecial("Meal3", "Chicken Potpie", "potpie", "237", "One of our most popular and top-rated meals of all time, this Classic Chicken Pot Pie has a flaky, buttery crust, a creamy sauce, and a hearty mix."));
        alMeals.add(new GetterSetterSpecial("Meal4", "Cream of Celery Soup", "cream", "87", "Very finely dice the onion and celery and mince the garlic."));
        alMeals.add(new GetterSetterSpecial("Meal5", "Hungarian Chicken Paprikash", "hungarian", "254", "This traditional Hungarian dish also known as paprikás csirke is made with browned chicken that is braised in a velvety rich "));
        alMeals.add(new GetterSetterSpecial("Meal6", "Potluck Macaroni and Cheese", "potluck", "45", "From ultra-creamy sauces to flavorful baked toppings, these different mac and cheese recipes have everything you crave."));
        alMeals.add(new GetterSetterSpecial("Meal7", "Shrimp Quesadilla", "shrimp", "202", "Shrimp quesadilla! Flour tortillas toasted with shredded Monterey Jack cheese and pan-seared shrimp with onions, cilantro, avocados."));
        alMeals.add(new GetterSetterSpecial("Meal8", "Traditional Meat Loaf", "meat", "222", "Topped with a sweet sauce, this traditional meat loaf recipe tastes so good that you might want to double it so everyone can have seconds."));

        alDrinks.add(new GetterSetterSpecial("Drink1", "Apple Juice", "apple", "23", "Apple juice is a fruit juice made by the maceration and pressing of an apple."));
        alDrinks.add(new GetterSetterSpecial("Drink2", "Beer", "beer", "67", "Beer is an alcoholic drink made from cereal grains like malted barley, corn, wheat, oats, rye, hops and rice. "));
        alDrinks.add(new GetterSetterSpecial("Drink3", "Coffee", "coffee", "9", "A beverage prepared from roasted coffee beans. Darkly colored, bitter, and slightly acidic, coffee has a stimulating effect."));
        alDrinks.add(new GetterSetterSpecial("Drink4", "Orange Juice", "orange", "87", "A liquid extract of the orange tree fruit, produced by squeezing or reaming oranges."));
        alDrinks.add(new GetterSetterSpecial("Drink5", "Red Wine", "wine", "123", "A type of alcoholic wine which is produced from fermentation of dark coloured grapes."));
        alDrinks.add(new GetterSetterSpecial("Drink6", "Sprite", "sprite", "32", "A clear, lemon and lime-flavored soft drink created by the Coca-Cola Company."));
        alDrinks.add(new GetterSetterSpecial("Drink7", "Tea", "tea", "43", "An aromatic beverage prepared by pouring hot or boiling water over cured or fresh leaves of Camellia sinensis."));
        alDrinks.add(new GetterSetterSpecial("Drink8", "Coke", "coke", "54", "A grey, hard, and porous coal-based fuel with a high carbon content and few impurities, made by heating coal or oil in the absence of air."));

        specialRev.setAdapter(new SpecialAdapter(getContext(), alSpecial));
        mealsRev.setAdapter(new MealAdapter(getContext(), alMeals));
        drinksRev.setAdapter(new DrinkAdapter(getContext(), alDrinks));
    }

}
