# WarpSigns
This plugin let your players travel with signs.  
Simply place 2 signs with [warp] on the first line and a name on the second line.  
When 2 signs have the same name, you can now use it to teleport from one sign to the other.  
[Download page on Spigot.com](https://www.spigotmc.org/resources/warpsigns.83013/)

### Permissions
<table>
  <tr>
    <th>Node</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>warpsigns.*</td>
    <td>All permissions.</td>
  </tr>
  <tr>
    <td>warpsigns.admin</td>
    <td>Reload the plugin.</td>
  </tr>
  <tr>
    <td>warpsigns.use</td>
    <td>To use the plugin.</td>
  </tr>
</table>

### Commands
/warpsigns or /ws
<table>
  <tr>
    <th>Node</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>/warpsigns reload</td>
    <td>Reload the plugin.</td>
  </tr>
  <tr>
    <td>/warpsigns listworlds</td>
    <td>List the worlds that you can place signs in.</td>
  </tr>
</table>

You can only use the signs you placed. (except if you have warpsigns.* or warpsigns.admin permission).

You can only delete the signs you placed. (except if you have warpsigns.* or warpsigns.admin permission).

You can place sign only in world that are in the config.yml.
world, world_nether and world_the_end are enable by default

It's my first plugin and there is probably a lots of code that could be improved. If you looked at the source code and found something that i could have done better, you're more than welcome to share your opinions. Please be consctructive.

Requests or help should be done in the discussion section and will be ignored if done in the reviews section.

If you find my plugin usefull and like it, please let a review.  
You can also make a little donation to help me! Thanks.  
[To let a review or a comment on Spigot.com](https://www.spigotmc.org/resources/warpsigns.83013/)

TODO:
- Add warp limit
- Add inter world configuration to disable teleport between world.
- Add locale
- Add update checker
- Add tab completion - DONE
