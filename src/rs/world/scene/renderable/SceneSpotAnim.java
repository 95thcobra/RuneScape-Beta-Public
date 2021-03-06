package rs.world.scene.renderable;

import rs.data.SpotAnim;
import rs.world.scene.Model;
import rs.world.scene.Renderable;

public final class SceneSpotAnim extends Renderable {

	public final SpotAnim spotanim;
	public int firstCycle;
	public int level;
	public int x;
	public int z;
	public int y;
	public int seqFrame;
	public int frameCycle;
	public boolean finished = false;

	public SceneSpotAnim(int x, int y, int z, int level, int spotanimIndex, int startCycle, int duration) {
		this.spotanim = SpotAnim.instance[spotanimIndex];
		this.level = level;
		this.x = x;
		this.z = z;
		this.y = y;
		this.firstCycle = startCycle + duration;
		this.finished = false;
	}

	public final void update(int cycle) {
		frameCycle += cycle;

		while (frameCycle > spotanim.seq.frameDelay[seqFrame]) {
			frameCycle -= spotanim.seq.frameDelay[seqFrame] + 1;
			seqFrame++;
			if (seqFrame >= spotanim.seq.frameCount) {
				seqFrame = 0;
				finished = true;
			}
		}
	}

	@Override
	public final Model getDrawModel() {
		Model m = new Model(spotanim.getModel(), false, true, !spotanim.disposeAlpha, true);

		if (!finished) {
			m.applyGroups();
			m.applyFrame((spotanim.seq.primaryFrames[seqFrame]));
			m.skinTriangle = null;
			m.labelVertices = null;
		}

		m.applyLighting(64, 850, -30, -50, -30, true);
		return m;
	}
}
