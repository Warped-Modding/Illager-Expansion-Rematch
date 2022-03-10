package me.sandbox.client.renders;

import me.sandbox.entity.ArchivistEntity;
import me.sandbox.entity.ArchivistEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;


public class ArchivistRender extends IllagerEntityRenderer<ArchivistEntity> {
    private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/archivist.png");
    public static final SpriteIdentifier BOOK_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/enchanting_table_book"));
    private final BookModel book;


    public ArchivistRender(EntityRendererFactory.Context context) {
        super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.EVOKER)), 0.5f);
        book = new BookModel(context.getPart(EntityModelLayers.BOOK));
        this.addFeature(new HeldItemFeatureRenderer<ArchivistEntity, IllagerEntityModel<ArchivistEntity>>((FeatureRendererContext)this){
            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ArchivistEntity ArchivistEntity, float f, float g, float h, float j, float k, float l) {
                if (ArchivistEntity.isSpellcasting()) {
                    super.render(matrixStack, vertexConsumerProvider, i, ArchivistEntity, f, g, h, j, k, l);
                }
                float bookanimation2 = 0.0f;
                float y = ((float)10 + MathHelper.cos((float)ArchivistEntity.age) *0.55f);
                matrixStack.push();
                matrixStack.translate(0.0D, 0.362D, -0.5f);
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270.0f));
                if (ArchivistEntity.isSpellcasting()) {
                    matrixStack.translate(-0.4D, 0.0, 0.0f);
                    book.setPageAngles(0.0f, y, 0.0f, 1.05f);
                    matrixStack.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(30.0f));
                } else {
                    book.setPageAngles(0.0F, MathHelper.clamp(bookanimation2, 0.0F, 1.0f), MathHelper.clamp(bookanimation2, 0.0F, 0.9F), bookanimation2);
                }
                VertexConsumer vertexConsumer = BOOK_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
                book.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
                matrixStack.pop();
            }});
        ((IllagerEntityModel)this.model).getHat().visible = true;
    }


    @Override
    public Identifier getTexture(ArchivistEntity entity) {
        return TEXTURE;
    }
}
