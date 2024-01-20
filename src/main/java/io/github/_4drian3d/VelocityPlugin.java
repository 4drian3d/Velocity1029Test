package io.github._4drian3d;

import com.google.inject.Inject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.time.Duration;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@Plugin(
		id = "test1029",
		name = "Test1029",
		description = "A Velocity Plugin Template",
		version = Constants.VERSION,
		authors = { "4drian3d" }
)
public final class VelocityPlugin {
    @Inject
    private CommandManager commandManager;

	@Subscribe
	void onProxyInitialization(final ProxyInitializeEvent event) {
		var node = BrigadierCommand.literalArgumentBuilder("test")
			.executes(ctx -> {
				final int uses = -1;
				final int lifetime = -1;
				execute(ctx.getSource(), uses, lifetime);
				return Command.SINGLE_SUCCESS;
			})
			.then(BrigadierCommand.requiredArgumentBuilder("uses", IntegerArgumentType.integer())
				.executes(ctx -> {
                    final int uses = IntegerArgumentType.getInteger(ctx, "uses");
                    final int lifetime = -1;
                    execute(ctx.getSource(), uses, lifetime);
                    return Command.SINGLE_SUCCESS;
                })
				.then(BrigadierCommand.requiredArgumentBuilder("lifetime", IntegerArgumentType.integer())
					.executes(ctx -> {
						final int uses = IntegerArgumentType.getInteger(ctx, "uses");
						final int lifetime = IntegerArgumentType.getInteger(ctx, "lifetime");
						execute(ctx.getSource(), uses, lifetime);
						return Command.SINGLE_SUCCESS;
					})
				)
			);

        commandManager.register(new BrigadierCommand(node));
	}

    private void execute(final CommandSource source, final int uses, final int lifetime) {
        source.sendMessage(miniMessage().deserialize("<rainbow>Hi " +System.currentTimeMillis())
                .clickEvent(ClickEvent.callback(aud -> {
                    aud.sendMessage(miniMessage().deserialize("""
                            <rainbow:!>Clicked</rainbow>
                            <aqua>Uses left: <white><uses>
                            <aqua>Time: <white><time>""",
                            Placeholder.parsed("uses", Integer.toString(uses)),
                            Placeholder.parsed("time", Integer.toString(lifetime))));
                }, ClickCallback.Options.builder()
                        .lifetime(lifetime == -1 ? ClickCallback.DEFAULT_LIFETIME : Duration.ofMillis(lifetime))
                                .uses(uses == -1 ? 100 : uses)
                        .build()))
        );
    }
}