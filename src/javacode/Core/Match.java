package javacode.Core;

import java.util.ArrayList;

import javax.json.JsonValue;

public class Match {

	public Autonomous[] autonomousData;
	public TeleOp[] teleopData;
	public Endgame[] endgameData;

	public class Autonomous extends MatchBase {

	}

	public class TeleOp extends MatchBase {

	}

	public class Endgame extends MatchBase {

	}

	public static MatchBase[] getMatchData(javax.json.JsonObject rawData, int size) {

		MatchBase[] fullMatchData = new MatchBase[size];

		String[] keys = rawData.keySet().toArray(new String[size]);
		for (int i = 0; i < size; i++) {
			Debugger.d(Match.class, "Key: " + keys[i]);

			javax.json.JsonArray jsonArray = rawData.getJsonArray(keys[i]);
			Debugger.d(Match.class, "Json array: " + jsonArray.toString());

			MatchBase match = new MatchBase();

			match.name = keys[i];
			match.datatype = MatchBase.DataType.valueOf(jsonArray.getString(0));

			final int endgameArrayValues = jsonArray.size();
			ArrayList<JsonValue> values = new ArrayList<JsonValue>();
			for (int k = 1; k < endgameArrayValues; k++) {
				values.add(jsonArray.get(k));
			}
			match.value = values;

			fullMatchData[i] = match;

		}

		return fullMatchData;
	}

	public static Autonomous[] matchBaseToAutonomous(MatchBase[] matchBase) {
		Autonomous[] autonomous = new Autonomous[matchBase.length];

		for (int i = 0; i < matchBase.length; i++) {
			Autonomous auto = new Match().new Autonomous();
			auto.name = "(Autonomous) " + matchBase[i].name;
			auto.datatype = matchBase[i].datatype;
			auto.value = matchBase[i].value;
			autonomous[i] = auto;
		}

		return autonomous;
	}

	public static TeleOp[] matchBaseToTeleOp(MatchBase[] matchBase) {
		TeleOp[] teleops = new TeleOp[matchBase.length];

		for (int i = 0; i < matchBase.length; i++) {
			TeleOp teleop = new Match().new TeleOp();
			teleop.name = matchBase[i].name;
			teleop.datatype = matchBase[i].datatype;
			teleop.value = matchBase[i].value;
			teleops[i] = teleop;
		}

		return teleops;
	}

	public static Endgame[] matchBaseToEndgame(MatchBase[] matchBase) {
		Endgame[] endgames = new Endgame[matchBase.length];

		for (int i = 0; i < matchBase.length; i++) {
			Endgame endgame = new Match().new Endgame();
			endgame.name = matchBase[i].name;
			endgame.datatype = matchBase[i].datatype;
			endgame.value = matchBase[i].value;
			endgames[i] = endgame;
		}

		return endgames;
	}

}
