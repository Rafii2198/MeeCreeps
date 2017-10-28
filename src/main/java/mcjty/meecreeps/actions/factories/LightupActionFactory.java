package mcjty.meecreeps.actions.factories;

import mcjty.meecreeps.actions.ActionOptions;
import mcjty.meecreeps.actions.IActionWorker;
import mcjty.meecreeps.actions.IActionFactory;
import mcjty.meecreeps.actions.workers.LightupActionWorker;
import mcjty.meecreeps.entities.EntityMeeCreeps;
import mcjty.meecreeps.varia.GeneralTools;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;

public class LightupActionFactory implements IActionFactory {

    @Override
    public boolean isPossible(World world, BlockPos pos) {
        // @todo config for area
        AxisAlignedBB box = new AxisAlignedBB(pos.add(-10, -5, -10), pos.add(10, 5, 10));
//        AxisAlignedBB box = new AxisAlignedBB(pos.add(-2, -2, -2), pos.add(2, 2, 2));
        return GeneralTools.traverseBoxTest(box, p -> {
            if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, world, p)) {
                int light = world.getLightFromNeighbors(p);
                if (light < 7) {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public boolean isPossibleSecondary(World world, BlockPos pos) {
        return false;
    }

    @Override
    public IActionWorker createWorker(EntityMeeCreeps entity, ActionOptions options) {
        return new LightupActionWorker(entity, options);
    }
}