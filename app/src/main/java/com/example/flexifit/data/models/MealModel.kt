package com.example.flexifit.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MealModel(
    val hits: List<Hit>,
    val _links: Links,
    val count: Int,
    val from: Int,
    val to: Int
)

class Links

data class Advanced(
    val category: String,
    val difficulty: String,
    val gender: String,
    val intensity: String,
    val muscle: String,
    val text_tutorials: List<TextTutorialX>,
    val title: String,
    val video_tutorials: List<String>
)

data class AdvancedX(
    val category: String,
    val difficulty: String,
    val gender: String,
    val muscle: String,
    val text_tutorials: List<TextTutorialX>,
    val title: String,
    val video_tutorials: List<String>
)

data class AllData(
    val data: List<Data>
)

data class Beginner(
    val category: String,
    val difficulty: String,
    val gender: String,
    val intensity: String,
    val muscle: String,
    val text_tutorials: List<TextTutorialX>,
    val title: String,
    val video_tutorials: List<String>
)

data class BeginnerX(
    val category: String,
    val difficulty: String,
    val gender: String,
    val muscle: String,
    val text_tutorials: List<TextTutorialX>,
    val title: String,
    val video_tutorials: List<String>
)


data class CHOCDF(
    val label: String,
    val quantity: Double,
    val unit: String
)


data class CHOLEX(
    val label: String,
    val quantity: Double,
    val unit: String
)


@Parcelize
data class Data(
    val category: String,
    val difficulty: String,
    val gender: String,
    val muscle: String,
    var counter: Boolean,
    var file_name: String?,
    var labels: List<String>?,
    var correct_label: String?,
    val text_tutorials: List<TextTutorial>,
    val title: String,
    val video_tutorials: List<String>
) : Parcelable

data class Day(
    val meals: List<Meal>,
    val nutrients: Nutrients
)

data class Digest(
    val daily: Double,
    val hasRDI: Boolean,
    val label: String,
    val schemaOrgTag: String,
    val sub: List<Sub>,
    val tag: String,
    val total: Double,
    val unit: String
)


data class Fast(
    val advanced: List<Data>,
    val beginner: List<Data>,
    val intermediate: List<Data>
)


data class Hit(
    val _links: LinksX,
    val recipe: Recipe
)

data class Images(
    val REGULAR: REGULAR,
    val SMALL: SMALL,
    val THUMBNAIL: THUMBNAIL
)

data class Ingredient(
    val food: String,
    val foodCategory: String,
    val foodId: String,
    val image: String,
    val measure: String,
    val quantity: Double,
    val text: String,
    val weight: Double
)


data class Intermediate(
    val category: String,
    val difficulty: String,
    val gender: String,
    val muscle: String,
    val text_tutorials: List<TextTutorialX>,
    val title: String,
    val video_tutorials: List<String>
)


data class item_model(
    val image: String,
    val title: String,
    val serving: String,
    val cal: String
)


data class LinksX(
    val self: Self
)


data class Lose(
    val fast: List<Fast>,
    val slow: List<Slow>
)


data class Maitain(
    val advanced: List<Data>,
    val beginner: List<Data>,
    val intermediate: List<Data>
)


data class Meal(
    val id: Int,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)


data class MealPlan(
    val meals: List<Meal>,
    val nutrients: Nutrients
)


data class Nutrients(
    val calories: Double,
    val carbohydrates: Double,
    val fat: Double,
    val protein: Double
)


class PlannerData : ArrayList<PlannerDataItem>()


data class PlannerDataItem(
    val lose: List<Lose>,
    val maitain: List<Maitain>
)


@Parcelize
data class Pose(
    val english_name: String,
    val sanskrit_name: String,
    val yoga_description: String,
    val image_url: String,
    val file_name: String,
    val labels: List<String>,
    val correct_label: String,
    val yoga_instruction: List<String>
) : Parcelable


data class Recipe(
    val mealType: List<String>,
    val calories: Double,
    val label: String,
    val cautions: List<Any>,
    @SerializedName("cuisineType")val cuisineType: List<String>,
    val dietLabels: List<String>,
    val digest: List<Digest>,
    val dishType: List<String>,
    val healthLabels: List<String>,
    val image: String,
    val images: Images,
    val ingredientLines: List<String>,
    val ingredients: List<Ingredient>,
    val shareAs: String,
    val source: String,
    val totalDaily: TotalDaily,
    val totalNutrients: TotalNutrients,
    val totalTime: Double,
    val totalWeight: Double,
    val uri: String,
    val url: String,
    val yield: Int
)


data class REGULAR(
    val height: Int,
    val url: String,
    val width: Int
)


data class Response(
    val id: Int? = null,
    val mobile: String,
    val name: String
)


data class Self(
    val href: String,
    val title: String
)


data class Slow(
    val advanced: List<Data>,
    val beginner: List<Data>,
    val intermediate: List<Data>
)


data class SMALL(
    val height: Int,
    val url: String,
    val width: Int
)


data class Sub(
    val daily: Double,
    val hasRDI: Boolean,
    val label: String,
    val schemaOrgTag: String,
    val tag: String,
    val total: Double,
    val unit: String
)


@Parcelize
data class TextTutorial(
    val text: String
) : Parcelable


data class TextTutorialX(
    val text: String
)


data class THUMBNAIL(
    val height: Int,
    val url: String,
    val width: Int
)


data class TotalDaily(
    val CA: CHOCDF,
    val CHOCDF: CHOCDF,
    val CHOLE: CHOCDF,
    val ENERC_KCAL: CHOCDF,
    val FASAT: CHOCDF,
    val FAT: CHOCDF,
    val FE: CHOCDF,
    val FIBTG: CHOCDF,
    val FOLDFE: CHOCDF,
    val K: CHOCDF,
    val MG: CHOCDF,
    val NA: CHOCDF,
    val NIA: CHOCDF,
    val P: CHOCDF,
    val PROCNT: CHOCDF,
    val RIBF: CHOCDF,
    val THIA: CHOCDF,
    val TOCPHA: CHOCDF,
    val VITA_RAE: CHOCDF,
    val VITB12: CHOCDF,
    val VITB6A: CHOCDF,
    val VITC: CHOCDF,
    val VITD: CHOCDF,
    val VITK1: CHOCDF,
    val ZN: CHOCDF
)


data class TotalNutrients(
    val CA: CHOLEX,
    val CHOCDF: CHOLEX,
    @SerializedName("CHOCDF.net") var CHOCDF2: CHOLEX,
    val CHOLE: CHOLEX,
    val ENERC_KCAL: CHOLEX,
    val FAMS: CHOLEX,
    val FAPU: CHOLEX,
    val FASAT: CHOLEX,
    val FAT: CHOLEX,
    val FATRN: CHOLEX,
    val FE: CHOLEX,
    val FIBTG: CHOLEX,
    val FOLAC: CHOLEX,
    val FOLDFE: CHOLEX,
    val FOLFD: CHOLEX,
    val K: CHOLEX,
    val MG: CHOLEX,
    val NA: CHOLEX,
    val NIA: CHOLEX,
    val P: CHOLEX,
    val PROCNT: CHOLEX,
    val RIBF: CHOLEX,
    val SUGAR: CHOLEX,
    @SerializedName("SUGAR.added") val SUGAR2: CHOLEX,
    @SerializedName("Sugar.alcohol") val Sugar3: CHOLEX,
    val THIA: CHOLEX,
    val TOCPHA: CHOLEX,
    val VITA_RAE: CHOLEX,
    val VITB12: CHOLEX,
    val VITB6A: CHOLEX,
    val VITC: CHOLEX,
    val VITD: CHOLEX,
    val VITK1: CHOLEX,
    val WATER: CHOLEX,
    val ZN: CHOLEX
)

data class YogaPoses(
    val poses: List<Pose>
)