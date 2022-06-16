package me.sandbox.client.renders;

import me.sandbox.entity.MarauderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

    @Environment(value= EnvType.CLIENT)
    public class MarauderRender
            extends IllagerEntityRenderer<MarauderEntity> {
        private static final Identifier TEXTURE = new Identifier("illagerexp:textures/entity/savager.png");

        public MarauderRender(EntityRendererFactory.Context context) {
            super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.VINDICATOR)), 0.5f);
            this.addFeature(new HeldItemFeatureRenderer<MarauderEntity, IllagerEntityModel<MarauderEntity>>((FeatureRendererContext)this, context.getHeldItemRenderer()));
        }

        @Override
        public Identifier getTexture(MarauderEntity vindicatorEntity) {
            return TEXTURE;
        }
    }
