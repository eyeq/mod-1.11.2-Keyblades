package eyeq.keyblades;

import eyeq.keyblades.block.BlockSynthesis;
import eyeq.keyblades.event.KeybladesEventHandler;
import eyeq.keyblades.item.ItemKeyblade;
import eyeq.keyblades.item.ItemKeybladeCritical;
import eyeq.keyblades.network.KeybladesGuiHandler;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.creativetab.UCreativeTab;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;

import static eyeq.keyblades.Keyblades.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class Keyblades {
    public static final String MOD_ID = "eyeq_keyblades";

    @Mod.Instance(MOD_ID)
    public static Keyblades instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static final int GUI_ID = 0;

    public static Item kingdomKey;
    public static final CreativeTabs TAB_KEYBLADES = new UCreativeTab("KeyBlades", () -> new ItemStack(kingdomKey));

    public static Block synthesis;

    public static Item jungleKing;
    public static Item threeWishes;
    public static Item crabclaw;
    public static Item pumpkinhead;
    public static Item fairyHarp;
    public static Item wishingStar;
    public static Item spellbinder;
    public static Item metalChocobo;
    public static Item olympia;
    public static Item lionheart;
    public static Item ladyLuck;
    public static Item divineRose;
    public static Item oathkeeper;
    public static Item oblivion;
    public static Item diamondDust;
    public static Item oneWingedAngel;
    public static Item ultimaWeapon;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new KeybladesEventHandler());
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new KeybladesGuiHandler());
    }

    @SubscribeEvent
    protected static void registerBlocks(RegistryEvent.Register<Block> event) {
        synthesis = new BlockSynthesis().setUnlocalizedName("synthesis");

        GameRegistry.register(synthesis, resource.createResourceLocation("synthesis"));
    }

    private static Item.ToolMaterial getMaterial(String name, float damage) {
        return EnumHelper.addToolMaterial(name, 2, 0, 8.0F, damage, 20);
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        kingdomKey = new ItemKeyblade(getMaterial("kingdomKey", 3.0F)).setUnlocalizedName("kingdomKey");
        jungleKing = new ItemKeyblade(getMaterial("jungleKing", 5.0F)).setReach(5.0F).setUnlocalizedName("jungleKing");
        threeWishes = new ItemKeyblade(getMaterial("threeWishes", 6.0F)).setUnlocalizedName("threeWishes");
        crabclaw = new ItemKeyblade(getMaterial("crabclaw", 6.0F)).setUnlocalizedName("crabclaw");
        pumpkinhead = new ItemKeyblade(getMaterial("pumpkinhead", 7.0F)).setReach(4.0F).setUnlocalizedName("pumpkinhead");
        fairyHarp = new ItemKeyblade(getMaterial("fairyHarp", 8.0F)).setReach(2.0F).setUnlocalizedName("fairyHarp");
        wishingStar = new ItemKeyblade(getMaterial("wishingStar", 5.0F)).setReach(2.0F).addEnchantmentData(new EnchantmentData(Enchantments.field_191530_r, 3)).setUnlocalizedName("wishingStar");
        spellbinder = new ItemKeyblade(getMaterial("spellbinder", 4.0F)).setUnlocalizedName("spellbinder");
        metalChocobo = new ItemKeyblade(getMaterial("metalChocobo", 9.0F)).setReach(4.0F).setUnlocalizedName("metalChocobo");
        olympia = new ItemKeyblade(getMaterial("olympia", 10.0F)).setUnlocalizedName("olympia");
        lionheart = new ItemKeyblade(getMaterial("lionheart", 10.0F)).setReach(4.0F).setUnlocalizedName("lionheart");
        ladyLuck = new ItemKeyblade(getMaterial("ladyLuck", 7.0F)).setUnlocalizedName("ladyLuck");
        divineRose = new ItemKeyblade(getMaterial("divineRose", 13.0F)).setUnlocalizedName("divineRose");
        oathkeeper = new ItemKeyblade(getMaterial("oathkeeper", 9.0F)).setUnlocalizedName("oathkeeper");
        oblivion = new ItemKeyblade(getMaterial("oblivion", 11.0F)).setReach(4.0F).setUnlocalizedName("oblivion");
        diamondDust = new ItemKeyblade(getMaterial("diamondDust", 3.0F)).setReach(2.5F).setUnlocalizedName("diamondDust");
        oneWingedAngel = new ItemKeybladeCritical(getMaterial("oneWingedAngel", 8.0F), 16.0F, 0.07F).setUnlocalizedName("oneWingedAngel");
        ultimaWeapon = new ItemKeyblade(getMaterial("ultimaWeapon", 14.0F)).setReach(4.0F).setUnlocalizedName("ultimaWeapon");

        GameRegistry.register(new ItemBlock(synthesis), synthesis.getRegistryName());

        GameRegistry.register(kingdomKey, resource.createResourceLocation("kingdom_key"));
        GameRegistry.register(jungleKing, resource.createResourceLocation("jungle_king"));
        GameRegistry.register(threeWishes, resource.createResourceLocation("three_wishes"));
        GameRegistry.register(crabclaw, resource.createResourceLocation("crabclaw"));
        GameRegistry.register(pumpkinhead, resource.createResourceLocation("pumpkinhead"));
        GameRegistry.register(fairyHarp, resource.createResourceLocation("fairy_harp"));
        GameRegistry.register(wishingStar, resource.createResourceLocation("wishing_star"));
        GameRegistry.register(spellbinder, resource.createResourceLocation("spellbinder"));
        GameRegistry.register(metalChocobo, resource.createResourceLocation("metal_chocobo"));
        GameRegistry.register(olympia, resource.createResourceLocation("olympia"));
        GameRegistry.register(lionheart, resource.createResourceLocation("lionheart"));
        GameRegistry.register(ladyLuck, resource.createResourceLocation("lady_luck"));
        GameRegistry.register(divineRose, resource.createResourceLocation("divine_rose"));
        GameRegistry.register(oathkeeper, resource.createResourceLocation("oathkeeper"));
        GameRegistry.register(oblivion, resource.createResourceLocation("oblivion"));
        GameRegistry.register(diamondDust, resource.createResourceLocation("diamond_dust"));
        GameRegistry.register(oneWingedAngel, resource.createResourceLocation("one-winged_angel"));
        GameRegistry.register(ultimaWeapon, resource.createResourceLocation("ultima_weapon"));
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(synthesis, "X X", "XXX", "XXX",
                'X', UOreDictionary.OREDICT_PLANKS));
    }

	@SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(synthesis);

        UModelLoader.setCustomModelResourceLocation(kingdomKey);
        UModelLoader.setCustomModelResourceLocation(jungleKing);
        UModelLoader.setCustomModelResourceLocation(threeWishes);
        UModelLoader.setCustomModelResourceLocation(crabclaw);
        UModelLoader.setCustomModelResourceLocation(pumpkinhead);
        UModelLoader.setCustomModelResourceLocation(fairyHarp);
        UModelLoader.setCustomModelResourceLocation(wishingStar);
        UModelLoader.setCustomModelResourceLocation(spellbinder);
        UModelLoader.setCustomModelResourceLocation(metalChocobo);
        UModelLoader.setCustomModelResourceLocation(olympia);
        UModelLoader.setCustomModelResourceLocation(lionheart);
        UModelLoader.setCustomModelResourceLocation(ladyLuck);
        UModelLoader.setCustomModelResourceLocation(divineRose);
        UModelLoader.setCustomModelResourceLocation(oathkeeper);
        UModelLoader.setCustomModelResourceLocation(oblivion);
        UModelLoader.setCustomModelResourceLocation(diamondDust);
        UModelLoader.setCustomModelResourceLocation(oneWingedAngel);
        UModelLoader.setCustomModelResourceLocation(ultimaWeapon);
    }
	
    public static void createFiles() {
    	File project = new File("../1.11.2-Keyblades");
    	
        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, "container.synthesis", "Synthesis");
        language.register(LanguageResourceManager.JA_JP, "container.synthesis", "シンセシス");

        language.register(LanguageResourceManager.EN_US, TAB_KEYBLADES, "Keyblades");
        language.register(LanguageResourceManager.JA_JP, TAB_KEYBLADES, "キーブレード");

        language.register(LanguageResourceManager.EN_US, synthesis, "Synthesis Table");
        language.register(LanguageResourceManager.JA_JP, synthesis, "合成台");

        language.register(LanguageResourceManager.EN_US, kingdomKey, "Kingdom Key");
        language.register(LanguageResourceManager.JA_JP, kingdomKey, "キングダムチェーン");
        language.register(LanguageResourceManager.EN_US, jungleKing, "Jungle King");
        language.register(LanguageResourceManager.JA_JP, jungleKing, "ネイティブワーク");
        language.register(LanguageResourceManager.EN_US, threeWishes, "Three Wishes");
        language.register(LanguageResourceManager.JA_JP, threeWishes, "デザイアーランプ");
        language.register(LanguageResourceManager.EN_US, crabclaw, "Crabclaw");
        language.register(LanguageResourceManager.JA_JP, crabclaw, "トレジャーオブシー");
        language.register(LanguageResourceManager.EN_US, pumpkinhead, "Pumpkinhead");
        language.register(LanguageResourceManager.JA_JP, pumpkinhead, "パンプキンヘッド");
        language.register(LanguageResourceManager.EN_US, fairyHarp, "Fairy Harp");
        language.register(LanguageResourceManager.JA_JP, fairyHarp, "フェアリーハープ");
        language.register(LanguageResourceManager.EN_US, wishingStar, "Wishing Star");
        language.register(LanguageResourceManager.JA_JP, wishingStar, "ウィッシュスター");
        language.register(LanguageResourceManager.EN_US, spellbinder, "Spellbinder");
        language.register(LanguageResourceManager.JA_JP, spellbinder, "エグザミネイション");
        language.register(LanguageResourceManager.EN_US, metalChocobo, "Metal Chocobo");
        language.register(LanguageResourceManager.JA_JP, metalChocobo, "メタルチョコボ");
        language.register(LanguageResourceManager.EN_US, olympia, "Olympia");
        language.register(LanguageResourceManager.JA_JP, olympia, "パワーオブヒーロー");
        language.register(LanguageResourceManager.EN_US, lionheart, "Lionheart");
        language.register(LanguageResourceManager.JA_JP, lionheart, "ライオンハート");
        language.register(LanguageResourceManager.EN_US, ladyLuck, "Lady Luck");
        language.register(LanguageResourceManager.JA_JP, ladyLuck, "ラストリゾート");
        language.register(LanguageResourceManager.EN_US, divineRose, "Divine Rose");
        language.register(LanguageResourceManager.JA_JP, divineRose, "ラヴィアンローズ");
        language.register(LanguageResourceManager.EN_US, oathkeeper, "Oathkeeper");
        language.register(LanguageResourceManager.JA_JP, oathkeeper, "約束のお守り");
        language.register(LanguageResourceManager.EN_US, oblivion, "Oblivion");
        language.register(LanguageResourceManager.JA_JP, oblivion, "過ぎ去りし思い出");
        language.register(LanguageResourceManager.EN_US, diamondDust, "Diamond Dust");
        language.register(LanguageResourceManager.JA_JP, diamondDust, "ダイヤモンドダスト");
        language.register(LanguageResourceManager.EN_US, oneWingedAngel, "One-Winged Angel");
        language.register(LanguageResourceManager.JA_JP, oneWingedAngel, "片翼の天使");
        language.register(LanguageResourceManager.EN_US, ultimaWeapon, "Ultima Weapon");
        language.register(LanguageResourceManager.JA_JP, ultimaWeapon, "アルテマウェポン");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createBlockstateJson(project, synthesis, "blocks/crafting_table_top");

        UModelCreator.createItemJson(project, kingdomKey, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, jungleKing, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, threeWishes, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, crabclaw, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, pumpkinhead, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, fairyHarp, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, wishingStar, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, spellbinder, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, metalChocobo, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, olympia, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, lionheart, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, ladyLuck, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, divineRose, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, oathkeeper, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, oblivion, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, diamondDust, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, oneWingedAngel, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, ultimaWeapon, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
    }
}
