package recipe

abstract class Food(val name: String) {
  override def toString: String = name
}

abstract class Recipe(
                       val name: String,
                       val ingredients: List[Food],
                       val instructions: String
                     ) {
  override def toString: String = name
}

abstract class Database extends FoodCategories {
  def allFoods: List[Food]

  def allRecipes: List[Recipe]

  def foodNamed(name: String): Option[Food] = allFoods.find(f => f.name == name)
}

abstract class Browser {
  val database: Database

  def recipesUsing(food: Food): List[Recipe] = database.allRecipes.filter(recipe => recipe.ingredients.contains(food))

  def displayCategory(category: database.FoodCategory): Unit = {
    println(category)
  }
}

trait FoodCategories {
  case class FoodCategory(name: String, foods: List[Food])

  def allCategories: List[FoodCategory]
}

trait SimpleFoods {
  object Apple extends Food("Apple")
  object Orange extends Food("Orange")
  object Cream extends Food("Sugar")
  object Sugar extends Food("Sugar")

  def allFoods = List(Apple, Orange, Cream, Sugar)
//  private val categories = List(
//    FoodCategory("fruits", List(Apple, Orange)),
//    FoodCategory("misc", List(Cream, Sugar))
//  )
//
//  def allCategories: List[SimpleDatabase.FoodCategory] = categories
  def allCategories = Nil
}

trait SimpleRecipes {
  this: SimpleFoods =>
  object FruitSalad extends Recipe(
    "fruit salad",
    List(Apple, Orange),
    "stir it all together."
  )

  def allRecipes: List[Recipe] = List(FruitSalad)
}

object SimpleDatabase extends Database with SimpleFoods with SimpleRecipes

object SimpleBrowser extends Browser {
  val database: Database = SimpleDatabase
}

object StudentDatabase extends Database {
  object Apple extends Food("Apple")
  object FrozenFood extends Food("ForozenFood")

  object HeatItUp extends Recipe("heat it up", List(FrozenFood), "Microwave the 'food for 10 minutes.")

  def allFoods: List[Food] = List(Apple, FrozenFood)

  def allRecipes: List[Recipe] = List(HeatItUp)

  def allCategories: List[StudentDatabase.FoodCategory] = List(
    FoodCategory("editable", List(FrozenFood))
  )
}

object StudentBrowser extends Browser {
  val database = StudentDatabase
}
