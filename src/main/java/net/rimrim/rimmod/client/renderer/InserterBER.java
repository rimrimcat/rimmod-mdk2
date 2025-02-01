package net.rimrim.rimmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.DebugInserterBlock;
import net.rimrim.rimmod.block.InserterBlock;
import net.rimrim.rimmod.block.properties.InserterState;
import net.rimrim.rimmod.blockentity.DebugInserterBlockEntity;
import net.rimrim.rimmod.blockentity.InserterBlockEntity;
import net.rimrim.rimmod.client.utils.TransformValue;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class InserterBER implements BlockEntityRenderer<InserterBlockEntity> {
    private final BlockEntityRendererProvider.Context context;

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "ber/inserter"), "");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "textures/ber/inserter.png");
    private final ModelPart root;
    private final ModelPart base;
    private final ModelPart grabber_right;
    private final ModelPart grabber_left;
    private final ModelPart arm_1;
    private final ModelPart arm_2;

    private final Quaternionf base_q = new Quaternionf(0, 0, 0, 1).normalize();

    private final Quaternionf grabber_right_q = new Quaternionf(0, -0.1400f, 0, 0.9902f).normalize();
    private final Quaternionf grabber_left_q = new Quaternionf(0, 0.1400f, 0, 0.9902f).normalize();
    private final float grabber_pivot_z = -0.3200f;

    private final Quaternionf arm_swing_q = new Quaternionf(0, 1, 0, 0).normalize();
    private final Quaternionf item_swing_q = new Quaternionf(0, -1, 0, 0).normalize();

    public InserterBER(BlockEntityRendererProvider.Context context) {
        this.context = context;

        this.root = context.bakeLayer(LAYER_LOCATION);
        this.base = root.getChild("base");
        this.grabber_right = root.getChild("grabber_right");
        this.grabber_left = root.getChild("grabber_left");
        this.arm_1 = root.getChild("arm_1");
        this.arm_2 = root.getChild("arm_2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(16, 21).addBox(-4.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 13).addBox(2.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 17).addBox(2.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-4.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(2.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 4).addBox(-2.0F, -3.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-3.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 21).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition grabber_right = partdefinition.addOrReplaceChild("grabber_right", CubeListBuilder.create(), PartPose.offset(-1.0F, 18.5F, -5.9473F));

        PartDefinition gright_r1 = grabber_right.addOrReplaceChild("gright_r1", CubeListBuilder.create().texOffs(8, 21).addBox(-1.0F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition grabber_left = partdefinition.addOrReplaceChild("grabber_left", CubeListBuilder.create(), PartPose.offset(1.0F, 18.5F, -5.9473F));

        PartDefinition gleft_r1 = grabber_left.addOrReplaceChild("gleft_r1", CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition arm_1 = partdefinition.addOrReplaceChild("arm_1", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition arm_1_r1 = arm_1.addOrReplaceChild("arm_1_r1", CubeListBuilder.create().texOffs(14, 13).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition arm_2 = partdefinition.addOrReplaceChild("arm_2", CubeListBuilder.create(), PartPose.offset(0.0F, 16.4672F, 0.0F));

        PartDefinition arm_2_r1 = arm_2.addOrReplaceChild("arm_2_r1", CubeListBuilder.create().texOffs(14, 7).addBox(-1.0F, 0.0328F, -4.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.294F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    private Quaternionf qnlerp(Quaternionf q_source, Quaternionf q_dest, float factor) {
        Quaternionf q_source_copy = new Quaternionf(q_source.x, q_source.y, q_source.z, q_source.w);
        return q_source_copy.nlerp(q_dest, factor);
    }

    @Override
    public void render(InserterBlockEntity blockEntity,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight,
                       int packedOverlay) {
        float animProgress;

        Level level = blockEntity.getLevel();
        if (level == null) return;

        InserterState state = blockEntity.getInserterState();
        Direction dir = blockEntity.getBlockState().getValue(InserterBlock.INSERT_DIRECTION).getOpposite();
        long lastUpdateInterval = level.getGameTime() - blockEntity.getLastUpdateTime();

        BlockPos pos = blockEntity.getBlockPos().above();
        int light_pack = LightTexture.pack(
                level.getBrightness(LightLayer.BLOCK, pos),
                level.getBrightness(LightLayer.SKY, pos)
        );


        // Common setup logic
        poseStack.pushPose();

        poseStack.translate(0.5f, 0.5f, 0.5f); // Generic translation
        poseStack.rotateAround(new Quaternionf(0, 0, -1, 0), 0f, 0.5f, 0f); // Align to block space

        // Render the base
        base.render(
                poseStack,
                bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)),
                light_pack,
                packedOverlay
        );

        poseStack.popPose();

        if (lastUpdateInterval > state.maxProgress) {
            animProgress = 1f;
        } else {
            animProgress = (float) lastUpdateInterval / state.maxProgress;
        }


        render_grabber(poseStack, bufferSource, dir,
                light_pack, packedOverlay,
                animProgress, state,
                grabber_left, grabber_left_q);
        render_grabber(poseStack, bufferSource, dir,
                light_pack, packedOverlay,
                animProgress, state,
                grabber_right, grabber_right_q);
        render_arm(poseStack, bufferSource, dir,
                light_pack, packedOverlay,
                animProgress, state,
                arm_1);
        render_arm(poseStack, bufferSource, dir,
                light_pack, packedOverlay,
                animProgress, state,
                arm_2);

        ItemStack stack = blockEntity.getItem(0);

        if (!stack.isEmpty()) {
            render_item_in_arm(poseStack, bufferSource, dir,
                    light_pack,
                    animProgress, state,
                    level, stack);
        }

    }

    private void renderModelPart(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            Direction dir,
            int packedLight,
            int packedOverlay,
            ModelPart modelPart,
            Runnable customTransformations
    ) {
        poseStack.pushPose();

        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.rotateAround(new Quaternionf(0, 0, -1, 0), 0f, 0.5f, 0f);

        // Rotate according to direction
        // I reversed the west and east because it orients the arm perfectly that way
        if (dir == Direction.EAST) {
            poseStack.rotateAround(new Quaternionf(0, -0.71f, 0, -0.71f), 0f, 0.5f, 0f);
        } else if (dir == Direction.WEST) {
            poseStack.rotateAround(new Quaternionf(0, 0.71f, 0, -0.71f), 0f, 0.5f, 0f);
        } else if (dir == Direction.SOUTH) {
            poseStack.rotateAround(new Quaternionf(0, 1f, 0, 0), 0f, 0.5f, 0f);
        }

        // Apply custom transformations unique to this model part
        if (customTransformations != null) {
            customTransformations.run();
        }

        modelPart.render(
                poseStack,
                bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)),
                packedLight,
                packedOverlay
        );

        poseStack.popPose();
    }

    public void render_grabber(PoseStack poseStack, MultiBufferSource bufferSource, Direction dir,
                               int packedLight, int packedOverlay,
                               float animProgress,
                               InserterState state,
                               ModelPart which_grabber,
                               Quaternionf which_grabber_q
    ) {
        renderModelPart(poseStack, bufferSource, dir, packedLight, packedOverlay, which_grabber, () -> {

            switch (state) {
                case TAKING: {
                    Quaternionf qlerp = qnlerp(base_q, which_grabber_q, animProgress);

                    poseStack.rotateAround(qlerp, 0, 0, grabber_pivot_z);
                    break;
                }
                case TRANSFERRING: {
                    Quaternionf qlerp = qnlerp(base_q, arm_swing_q, animProgress);
                    poseStack.rotateAround(qlerp, 0, 0, 0);
                    poseStack.rotateAround(which_grabber_q, 0, 0, grabber_pivot_z);
                    break;
                }
                case RETURNING: {
                    Quaternionf qlerp = qnlerp(arm_swing_q, base_q, animProgress);
                    poseStack.rotateAround(qlerp, 0, 0, 0);
                    break;
                }
                case WAIT_DESTINATION: {
                    poseStack.rotateAround(arm_swing_q, 0, 0, 0);
                    poseStack.rotateAround(which_grabber_q, 0, 0, grabber_pivot_z);
                    break;
                }
                case INSERTING: {
                    poseStack.rotateAround(arm_swing_q, 0, 0, 0);
                    Quaternionf qlerp = qnlerp(which_grabber_q, base_q, animProgress);
                    poseStack.rotateAround(qlerp, 0, 0, grabber_pivot_z);
                    break;
                }

            }


        });
    }


    public void render_arm(PoseStack poseStack, MultiBufferSource bufferSource, Direction dir,
                           int packedLight, int packedOverlay,
                           float animProgress,
                           InserterState state,
                           ModelPart which_arm
    ) {
        renderModelPart(poseStack, bufferSource, dir, packedLight, packedOverlay, which_arm, () -> {

            switch (state) {
                case TRANSFERRING: {
//                    Quaternionf qlerp = new Quaternionf(arm_swing_q.x, arm_swing_q.y, arm_swing_q.z, arm_swing_q.w).nlerp(base_q, animProgress);
                    Quaternionf qlerp = qnlerp(base_q, arm_swing_q, animProgress);
                    poseStack.rotateAround(qlerp, 0, 0, 0);
                    break;
                }
                case RETURNING: {
//                     Quaternionf qlerp = arm_swing_q.nlerp(base_q, animProgress);
//                     Quaternionf qlerp = base_q.nlerp(arm_swing_q, animProgress);
                    Quaternionf qlerp = qnlerp(arm_swing_q, base_q, animProgress);
                    poseStack.rotateAround(qlerp, 0, 0, 0);
                    break;
                }
                case WAIT_DESTINATION: {
                }
                case INSERTING: {
                    poseStack.rotateAround(arm_swing_q, 0, 0, 0);
                    break;
                }

            }
        });
    }

    private void render_item_in_arm(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            Direction dir,
            int packedLight,
            float animProgress,
            InserterState state,
            Level level, ItemStack stack
    ) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.3125f, 0.5f); // To center

        if (dir == Direction.EAST) {
            poseStack.rotateAround(new Quaternionf(0, 0.71f, 0, -0.71f), 0f, 0.5f, 0f);
        } else if (dir == Direction.WEST) {
            poseStack.rotateAround(new Quaternionf(0, -0.71f, 0, -0.71f), 0f, 0.5f, 0f);
        } else if (dir == Direction.SOUTH) {
            poseStack.rotateAround(new Quaternionf(0, 1f, 0, 0), 0f, 0.5f, 0f);
        }

        poseStack.translate(0f, 0, -0.5f); // To inserter hand at SOUTH


        if (state == InserterState.TRANSFERRING) {
            Quaternionf qlerp = qnlerp(base_q, item_swing_q, animProgress);
            poseStack.rotateAround(qlerp, 0, 0, 0.5f);
        } else {
            poseStack.rotateAround(item_swing_q, 0, 0, 0.5f);
        }

        poseStack.scale(1 / 4f, 1 / 4f, 1 / 4f); // Make smaller


        this.context.getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                level,
                0
        );

        poseStack.popPose();
    }


}
