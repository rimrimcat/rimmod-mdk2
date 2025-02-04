package net.rimrim.rimmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.rimrim.rimmod.blockentity.ChemicalTankBlockEntity;
import net.rimrim.rimmod.chem.stack.ChemicalStackHandler;
import net.rimrim.rimmod.chem.enums.MatterState;
import org.joml.Quaternionf;

public class ChemicalTankBER implements BlockEntityRenderer<ChemicalTankBlockEntity> {
    private final BlockEntityRendererProvider.Context context;

    public ChemicalTankBER(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }


    @Override
    public void render(ChemicalTankBlockEntity blockEntity,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight,
                       int packedOverlay) {

        Level level = blockEntity.getLevel();
        if (level == null) return;

        BlockPos pos = blockEntity.getBlockPos().above();
        int light_pack = LightTexture.pack(
                level.getBrightness(LightLayer.BLOCK, pos),
                level.getBrightness(LightLayer.SKY, pos)
        );

        ChemicalStackHandler chemHandler = blockEntity.getChemHandler();

        if (chemHandler.getChemicalStack().isEmpty()) return;
        if (!(chemHandler.getChemicalStack().state() == MatterState.LIQUID)) return;

        // LIQUIDS ONLY FOR NOW
        // TODO: VAPORS
        ResourceLocation stillTexture = IClientFluidTypeExtensions.of(Fluids.WATER).getStillTexture();
        int color = chemHandler.getChemicalStack().chemical().color;
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(stillTexture);

        VertexConsumer builder = bufferSource.getBuffer(RenderType.TRANSLUCENT);
        drawFull(builder, poseStack,
                4 / 16f, 1 / 16f, 4 / 16f,
                12 / 16f, (12 / 16f) * chemHandler.fillRatio(), 12 / 16f,
                sprite.getU0(), sprite.getV0(),
                sprite.getU1(), sprite.getV1(),
                light_pack, 15, color
        );


    }

    private static void drawVertex(VertexConsumer builder, PoseStack pose,
                                   float x, float y, float z,
                                   float u, float v,
                                   float nx, float ny, float nz,
                                   int light_pack, int packedOverlay,
                                   int color
    ) {
        builder.addVertex(pose.last().pose(), x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setLight(light_pack)
                .setOverlay(packedOverlay)
                .setNormal(nx, ny, nz);
    }

    private static void drawQuad(VertexConsumer builder, PoseStack pose,
                                 float x1, float y1, float z1,
                                 float x2, float y2, float z2,
                                 float u1, float v1,
                                 float u2, float v2,
                                 float nx, float ny, float nz,
                                 int light_pack, int packedOverlay,
                                 int color) {

        drawVertex(builder, pose,
                x1, y1, z1,
                u1, v1, nx, ny, nz, light_pack, packedOverlay, color);
        drawVertex(builder, pose,
                x1, y2, z2,
                u1, v2, nx, ny, nz, light_pack, packedOverlay, color);
        drawVertex(builder, pose,
                x2, y2, z2,
                u2, v2, nx, ny, nz, light_pack, packedOverlay, color);
        drawVertex(builder, pose,
                x2, y1, z1,
                u2, v1, nx, ny, nz, light_pack, packedOverlay, color);

    }

    private static void drawFull(VertexConsumer builder, PoseStack pose,
                                 float x1, float y1, float z1,
                                 float x2, float y2, float z2,
                                 float u1, float v1,
                                 float u2, float v2,
                                 int light_pack, int packedOverlay,
                                 int color) {

        float dy = y2 - y1;
        float dl = x2 - x1;

        // ROTATION CENTER
        float cx = (x1 + x2) / 2;
        float cy = y2;
        float cz = (z1 + z2) / 2;

        // TOP
        pose.pushPose();
        drawQuad(builder, pose,
                x1, y2, z1, x2, y2, z2,
                u1, v1, u2, v2,
                0, 1, 0,
                light_pack, packedOverlay, color);
        pose.popPose();

        // +X
        pose.pushPose();
        pose.rotateAround(new Quaternionf(0, 0, -0.71, 0.71), cx, cy, cz); // rotate
        pose.translate(dl / 2, dl / 2, 0); // move to side and adjust vert
        drawQuad(builder, pose,
                x1, y2, z1, x1 + dy, y2, z2,
                u1, v1, u2, v2,
                0, 1, 0,
                light_pack, packedOverlay, color);
        pose.popPose();

        // -X
        pose.pushPose();
        pose.rotateAround(new Quaternionf(0, 0, 0.71, 0.71), cx, cy, cz);
        pose.translate(dl / 2, dl / 2, 0); // move to side and adjust vert
        drawQuad(builder, pose,
                x1 - dy, y2, z1, x1, y2, z2,
                u1, v1, u2, v2,
                0, 1, 0,
                light_pack, packedOverlay, color);
        pose.popPose();

        // +Z
        pose.pushPose();
        pose.rotateAround(new Quaternionf(0.71, 0, 0, 0.71), cx, cy, cz);
        pose.translate(0, dl / 2, dl / 2); // move to side and adjust vert
        drawQuad(builder, pose,
                x1, y2, z1, x2, y2, z1 + dy,
                u1, v1, u2, v2,
                0, 1, 0,
                light_pack, packedOverlay, color);
        pose.popPose();
        // -Z
        pose.pushPose();
        pose.rotateAround(new Quaternionf(-0.71, 0, 0, 0.71), cx, cy, cz);
        pose.translate(0, dl / 2, dl / 2); // move to side and adjust vert
        drawQuad(builder, pose,
                x1, y2, z1 - dy, x2, y2, z1,
                u1, v1, u2, v2,
                0, 1, 0,
                light_pack, packedOverlay, color);
        pose.popPose();


    }

}
