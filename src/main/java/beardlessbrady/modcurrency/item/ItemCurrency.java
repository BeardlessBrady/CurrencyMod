package beardlessbrady.modcurrency.item;

import beardlessbrady.modcurrency.ConfigCurrency;
import beardlessbrady.modcurrency.ModCurrency;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by BeardlessBrady. It is distributed as
 * part of The Currency-Mod. Source Code located on github:
 * https://github.com/BeardlessBrady/Currency-Mod
 * -
 * Copyright (C) All Rights Reserved
 * File Created 2019-02-07
 */

public class ItemCurrency extends Item {
    public static final int currencyLength = ConfigCurrency.currencyValues.length;

    public ItemCurrency(){
        setUnlocalizedName("currency");
        setRegistryName("currency");
        setHasSubtypes(true);
    }

    public int getCurrencyValue(ItemStack stack){
        return Integer.parseInt(ConfigCurrency.currencyValues[stack.getItemDamage()]);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        for (int i = 0; i < currencyLength; i++) {
            ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(getRegistryName() + "_" + i, "inventory"));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "Item." + this.getRegistryName().toString() + "_" + stack.getItemDamage();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if(stack.getItemDamage() >= ConfigCurrency.currencyNames.length){
            return "SOMETHING WENT WRONG: ITEM DAMAGE TOO HIGH.";
        }else return ConfigCurrency.currencyNames[stack.getItemDamage()];
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == ModCurrency.tabCurrency){
            for (int i = 0; i < currencyLength; i++) {
                ItemStack stack = new ItemStack(this, 1, i);
                items.add(stack);
            }
        }
    }
}