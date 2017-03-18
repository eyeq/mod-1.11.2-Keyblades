package eyeq.keyblades.item.crafting;

import eyeq.keyblades.Keyblades;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class SynthesisManager {
    private static final SynthesisManager instance = new SynthesisManager();

    private final Map<ItemStack, ItemStack> synthesisRecipes = new HashMap<>();
    private final Map<String, ItemStack> synthesisOreRecipes = new HashMap<>();

    public static SynthesisManager getInstance() {
        return instance;
    }

    private SynthesisManager() {
        init();
    }

    private void init() {
        addSynthesis(new ItemStack(Keyblades.jungleKing), UOreDictionary.OREDICT_VINE);
        addSynthesis(new ItemStack(Keyblades.threeWishes), UOreDictionary.OREDICT_GOLD_INGOT);
        addSynthesis(new ItemStack(Keyblades.crabclaw), Items.SHULKER_SHELL);
        addSynthesis(new ItemStack(Keyblades.pumpkinhead), Blocks.PUMPKIN);
        addSynthesis(new ItemStack(Keyblades.fairyHarp), UOreDictionary.OREDICT_FEATHER);
        addSynthesis(new ItemStack(Keyblades.wishingStar), UOreDictionary.OREDICT_NETHER_STAR);
        addSynthesis(new ItemStack(Keyblades.spellbinder), Items.BOOK);
        addSynthesis(new ItemStack(Keyblades.metalChocobo), UOreDictionary.OREDICT_EGG);
        addSynthesis(new ItemStack(Keyblades.olympia), UOreDictionary.OREDICT_GOLD_BLOCK);
        addSynthesis(new ItemStack(Keyblades.lionheart), UOreDictionary.OREDICT_IRON_BLOCK);
        addSynthesis(new ItemStack(Keyblades.ladyLuck), UOreDictionary.OREDICT_QUARTZ);
        addSynthesis(new ItemStack(Keyblades.divineRose), new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.POPPY.getMeta()));
        addSynthesis(new ItemStack(Keyblades.oathkeeper), UOreDictionary.OREDICT_DIAMOND);
        addSynthesis(new ItemStack(Keyblades.oblivion), UOreDictionary.OREDICT_OBSIDIAN);
        addSynthesis(new ItemStack(Keyblades.diamondDust), Items.SNOWBALL);
        addSynthesis(new ItemStack(Keyblades.oneWingedAngel), Items.ELYTRA);
        addSynthesis(new ItemStack(Keyblades.ultimaWeapon), "darkMatter");
    }

    public void addSynthesis(ItemStack output, ItemStack input) {
        this.synthesisRecipes.put(input, output);
    }

    public void addSynthesis(ItemStack output, Block input) {
        this.addSynthesis(output, new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE));
    }

    public void addSynthesis(ItemStack output, Item input) {
        this.addSynthesis(output, new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE));
    }

    public void addSynthesis(ItemStack output, String input) {
        this.synthesisOreRecipes.put(input, output);
    }

    public ItemStack getResult(ItemStack input) {
        for(ItemStack itemStack : synthesisRecipes.keySet()) {
            if(itemStack.getItem() == input.getItem()) {
                if(itemStack.getMetadata() == OreDictionary.WILDCARD_VALUE || itemStack.getMetadata() == input.getMetadata()) {
                    return synthesisRecipes.get(itemStack);
                }
            }
        }
        for(String ore : synthesisOreRecipes.keySet()) {
            if(UOreDictionary.contains(input, ore)) {
                return synthesisOreRecipes.get(ore);
            }
        }
        return ItemStack.EMPTY;
    }
}
