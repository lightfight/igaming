// Builder Pattern - Pages 14-15
package org.effectivejava.examples.chapter02.item02.builder;

/**
 * 1.类的所有属性都是final的，必须在构造方法里进行设值,这样可以保证类的不变性;
 * 2.构造方法是private私有的
 * 3.从结构上讲,一个类里面包含一个`public static class Builder`，Builder的构建方法传入的是必须参数，
 * 	 非必须参数的方法名就是属性名本身，设值后返回Builder对象本身，方便再次调用
 * 4.递变过程：重叠构建器模式-->JavaBeans模式--Builder模式
 */
public class NutritionFacts {
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;

	public static class Builder {
		// Required parameters
		private final int servingSize;
		private final int servings;

		// Optional parameters - initialized to default values
		private int calories = 0;
		private int fat = 0;
		private int carbohydrate = 0;
		private int sodium = 0;

		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}

		public Builder calories(int val) {
			calories = val;
			return this;
		}

		public Builder fat(int val) {
			fat = val;
			return this;
		}

		public Builder carbohydrate(int val) {
			carbohydrate = val;
			return this;
		}

		public Builder sodium(int val) {
			sodium = val;
			return this;
		}

		public NutritionFacts build() {
			return new NutritionFacts(this);
		}
	}

	private NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}

	public static void main(String[] args) {
		NutritionFacts cocaCola = new Builder(240, 8)
				.calories(100).sodium(35).carbohydrate(27).build();
	}
}