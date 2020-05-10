/*
 * Copyright 2016 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.coresamplegameplay;

import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.inventory.InventoryComponent;
import org.terasology.logic.inventory.InventoryManager;
import org.terasology.logic.inventory.ItemComponent;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.registry.In;
import org.terasology.world.block.BlockManager;
import org.terasology.world.block.items.BlockItemFactory;

@RegisterSystem(RegisterMode.AUTHORITY)
public class PlayerStartingInventorySystem extends BaseComponentSystem {
    @In
    BlockManager blockManager;
    @In
    InventoryManager inventoryManager;
    @In
    EntityManager entityManager;

    @ReceiveEvent(components = InventoryComponent.class)
    public void onPlayerSpawnedEvent(OnPlayerSpawnedEvent event, EntityRef player) {
        BlockItemFactory blockFactory = new BlockItemFactory(entityManager);

        EntityRef bonusChest = getBonusChest(blockFactory);
        EntityRef explosivesChest = getExplosivesChest(blockFactory);
        EntityRef improvedToolsChest = getImprovedToolsChest(blockFactory);

        inventoryManager.giveItem(player, EntityRef.NULL, entityManager.create("CoreAssets:pickaxe"));
        inventoryManager.giveItem(player, EntityRef.NULL, entityManager.create("CoreAssets:axe"));
        inventoryManager.giveItem(player, EntityRef.NULL, entityManager.create("CoreAssets:shovel"));
        inventoryManager.giveItem(player, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:Torch"), 99));
        inventoryManager.giveItem(player, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreSampleGameplay:companion"), 99));
        inventoryManager.giveItem(player, EntityRef.NULL, bonusChest);
        inventoryManager.giveItem(player, EntityRef.NULL, improvedToolsChest);
        inventoryManager.giveItem(player, EntityRef.NULL, explosivesChest);
    }

    private EntityRef getImprovedToolsChest(BlockItemFactory blockFactory) {
        EntityRef chest = blockFactory.newInstance(blockManager.getBlockFamily("CoreAdvancedAssets:chest"));
        chest.addComponent(new InventoryComponent(30));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAssets:pickaxeImproved"));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAssets:axeImproved"));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAssets:shovelImproved"));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAssets:gooeysFist"));
        return chest;
    }

    private EntityRef getExplosivesChest(BlockItemFactory blockFactory) {
        EntityRef chest = blockFactory.newInstance(blockManager.getBlockFamily("CoreAdvancedAssets:chest"));
        chest.addComponent(new InventoryComponent(30));
        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAdvancedAssets:Tnt"), 99));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAdvancedAssets:fuseShort"));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAdvancedAssets:fuseLong"));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAdvancedAssets:dynamite"));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAdvancedAssets:dynamiteBundle"));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAdvancedAssets:gun"));
        inventoryManager.giveItem(chest, EntityRef.NULL, entityManager.create("CoreAdvancedAssets:gunImproved"));

        return chest;
    }

    private EntityRef getBonusChest(BlockItemFactory blockFactory) {
        EntityRef chest = blockFactory.newInstance(blockManager.getBlockFamily("CoreAdvancedAssets:chest"));
        chest.addComponent(new InventoryComponent(30));

        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:Brick:engine:stair"), 99));
        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:Cobblestone"), 99));
        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:Plank"), 99));

        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:lava"), 99));
        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:water"), 99));
        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:Ice"), 99));

        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:Iris"), 99));
        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:Dandelion"), 99));
        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:Tulip"), 99));
        inventoryManager.giveItem(chest, EntityRef.NULL, blockFactory.newInstance(blockManager.getBlockFamily("CoreAssets:YellowFlower"), 99));

        EntityRef doorItem = entityManager.create("CoreAdvancedAssets:door");
        ItemComponent doorItemComp = doorItem.getComponent(ItemComponent.class);
        doorItemComp.stackCount = 20;
        doorItem.saveComponent(doorItemComp);
        inventoryManager.giveItem(chest, EntityRef.NULL, doorItem);

        return chest;
    }
}
