package com.mineblock11.armorful.client.wolves.renderer;

import com.mineblock11.armorful.client.wolves.WolfArmorClient;
import com.mineblock11.armorful.client.wolves.model.WolfArmorModel;
import com.mineblock11.armorful.item.DyeableWolfArmorItem;
import com.mineblock11.armorful.item.WolfArmorItem;
import com.mineblock11.armorful.util.ArmorfulUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class WolfArmorFeatureRenderer extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {

    private final WolfArmorModel model;

    public WolfArmorFeatureRenderer(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> featureRendererContext, EntityModelLoader loader) {
        super(featureRendererContext);
        model = new WolfArmorModel(loader.getModelPart(WolfArmorClient.WOLF_ARMOR));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, WolfEntity wolf, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = ArmorfulUtil.getWolfData(wolf).getWolfArmor();

        if (itemStack.getItem() instanceof WolfArmorItem armorItem) {
            this.getContextModel().copyStateTo(this.model);

            this.model.animateModel(wolf, f, g, h);
            this.model.setAngles(wolf, f, g, j, k, l);

            float q;
            float r;
            float s;

            if (armorItem instanceof DyeableWolfArmorItem dyeable) {
                int m = dyeable.getColor(itemStack);
                q = (float) (m >> 16 & 255) / 255.0F;
                r = (float) (m >> 8 & 255) / 255.0F;
                s = (float) (m & 255) / 255.0F;
            } else {
                q = 1.0F;
                r = 1.0F;
                s = 1.0F;
            }

            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(armorItem.getEntityTexture()), false, itemStack.hasEnchantments());
            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, q, r, s, 1.0F);
        }
    }
}
