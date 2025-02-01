package net.rimrim.rimmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.ItemLike;
import net.rimrim.rimmod.init.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ModRecipeProvider extends RecipeProvider  {

    // TODO:
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Rim Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM),
                RecipeCategory.MISC,
                ModItems.EXAMPLE_BLOCK_ITEM)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ModItems.EXAMPLE_ITEM)
                .unlockedBy("has_example_item", has(ModItems.EXAMPLE_ITEM))
                .save(this.output);
        ;

        List<ItemLike> EXAMPLE_SMELTABLES = List.of(ModItems.EXAMPLE_RAW, ModItems.EXAMPLE_ITEM);



        //
        oreSmelting(EXAMPLE_SMELTABLES, RecipeCategory.MISC, ModItems.EXAMPLE_BLOCK_ITEM.get(), 0.25f, 200, "example_smelting_group");
    }


}
