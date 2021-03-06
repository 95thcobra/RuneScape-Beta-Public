package rs.world.scene.renderable;

import rs.data.Npc;
import rs.data.Seq;
import rs.data.SpotAnim;
import rs.world.scene.Model;

public final class SceneNpc extends SceneEntity {

	public Npc info;

	@Override
	public final Model getDrawModel() {
		if (info == null) {
			return null;
		}

		if (spotanimIndex == -1 || spotanimFrame == -1) {
			return getModel();
		}

		SpotAnim spotanim = SpotAnim.instance[spotanimIndex];
		Model m = new Model(spotanim.getModel(), false, true, !spotanim.disposeAlpha, true);

		m.translate(0, -spotanimOffsetY, 0);

		m.applyGroups();
		m.applyFrame(spotanim.seq.primaryFrames[spotanimFrame]);

		m.skinTriangle = null;
		m.labelVertices = null;

		m.applyLighting(64, 850, -30, -50, -30, true);
		return new Model(new Model[]{getModel(), m}, 2, true, 20525);
	}

	public final Model getModel() {
		if (primarySeq >= 0 && primarySeqDelay == 0) {
			int frame1 = Seq.instances[primarySeq].primaryFrames[primarySeqFrame];
			int frame2 = -1;

			if (secondarySeq >= 0 && secondarySeq != standSeq) {
				frame2 = (Seq.instances[secondarySeq].primaryFrames[secondarySeqFrame]);
			}

			return info.getModel(frame1, frame2, Seq.instances[primarySeq].labelGroups);
		}

		int frame = -1;

		if (secondarySeq >= 0) {
			frame = Seq.instances[secondarySeq].primaryFrames[secondarySeqFrame];
		}

		Model m = info.getModel(frame, -1, null);
		height = m.maxBoundY;
		return m;
	}

	public final boolean isValid() {
		return info != null;
	}
}
