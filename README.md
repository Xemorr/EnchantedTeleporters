# Enchanted Teleporters
This plugin is best explained through a video!
[Gif showing that punching teleports you in the direction you're looking, jumping sends you upwards, and sneaking sends you downwards](https://cdn.modrinth.com/data/cached_images/0347404d5fa24732ccdd85b50966bbd005472ab2.gif)

## Config
```yaml
# This item can safely be changed without old teleporter items breaking.
teleporter:
  # If you change the type from BEACON, the plugin will stop working.
  # I'm planning on adding support for custom block plugins later.
  type: BEACON
  metadata:
    displayName: <light_purple>Teleporter
    lore:
      - "<gray>Berembert's grand invention!"
      - "<gray>Jumping, sends you to the above teleporter"
      - "<gray>Sneaking, sends you to the below teleporter"
      - "<gray>Punching, sends you to the ahead teleporter"
teleported: "<aqua><b>You have been teleported!"
sidewaysTeleportError: "<red><b>ERROR</b>: <gray>The teleporter you are aiming at is too far away. It needs to be under 90 blocks away!"
```

## Commands
/teleporters give <name> <amount>

/teleporters reload

## Permissions
enchantedteleporters.reload

enchantedteleporters.give
