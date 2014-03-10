import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IslandGenerator {
	int height;
	int width;
	int seed;
	int smoothen;
	double[][] map;
	double[][] particleMap;
	
	public void setSmooth(int in){
		smoothen = in;
	}
	
	public double[][] getMap() {
		return particleMap;
	}

	public IslandGenerator(int inWid, int inHei) {
		width = inWid;
		height = inHei;
		seed = 0;
		smoothen = 4;
		map = new double[height][width];
		particleMap = new double[height][width];
	}

	public IslandGenerator(int inWid, int inHei, int inSeed) {
		width = inWid;
		height = inHei;
		seed = inSeed;
		map = new double[height][width];
		particleMap = new double[height][width];
	}

	// @SuppressWarnings("static-access")
	public double[][] generateIsland(int inWid, int inHei, double inFreq, int inOct) {
		// generator noise
		PerlinNoise png;// = new PerlinNoise(height, width, seed);
		Random random;// = new Random(seed);
		if (seed != 0) {
			png = new PerlinNoise(height, width, seed);
			random = new Random(seed);
		} else {
			png = new PerlinNoise(height, width);
			random = new Random();
		}

		map = png.generate_noise(inWid, inHei, inFreq, inOct);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				particleMap[y][x] = 0.0;// (int) (((png.noise(x, y) + 1) * .5) * 255);
			}
		}

		// for (int q = 0; q < height; q++) {
		// for (int r = 0; r < width; r++) {
		// System.out.println(r + "," + q);
		// particleMap[q][r] = 0;
		//
		// not sure
		for (int i = 0; i < (height * width) * .85; i++) {
//			int x = (int)((random.nextInt(width) - (width/2)) * .55) + (width/2);//(width / 16) + random.nextInt(width - (width / 8));// rand from 15 to width-16
//			int y = (int)((random.nextInt(height) - (height/2)) * .55) + (height/2);//(height / 16) + random.nextInt(height - (height / 8));// rand from 15 to width-16
			int x = (width / 16) + random.nextInt(width - (width / 8));// rand from 15 to width-16
			int y = (height / 16) + random.nextInt(height - (height / 8));// rand from 15 to width-16
//			 int x = random.nextInt(width);
//			 int y = random.nextInt(height);

			for (int j = 0; j < (height * width) * .15; j++) {
//				 System.out.println(j + "," + i);
				particleMap[y][x] += 7;
				if (particleMap[y][x] >= 255)
					particleMap[y][x] = 255;
				double current_value = particleMap[y][x];

				boolean oneTrue = false;
				List<String> choices = new ArrayList<>();// [4];
				if (x - 1 > 0)
					if (particleMap[y][x - 1] <= current_value) {
						choices.add("left");
						oneTrue = true;
					}
				if (x + 1 < width - 1)
					if (particleMap[y][x + 1] <= current_value) {
						choices.add("right");
						oneTrue = true;
					}
				if (y - 1 > 0)
					if (particleMap[y - 1][x] <= current_value) {
						choices.add("up");
						oneTrue = true;
					}
				if (y + 1 < height - 1)
					if (particleMap[y + 1][x] <= current_value) {
						choices.add("down");
						oneTrue = true;
					}

				if (!oneTrue)// one of the 4 if statements above
					break;

				int randInt = random.nextInt(choices.size());
				String newString = choices.get(randInt);
				if (newString == "left")
					x -= 1;
				else if (newString == "right")
					x += 1;
				else if (newString == "up")
					y -= 1;
				else if (newString == "down")
					y += 1;

			}
		}
		// }
		// }

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				map[y][x] *= ((particleMap[y][x]) / 255.0);
			}
		}

		for (int y = 0; y < smoothen; y++) {
			smoothen();
		}

		return map;
	}

	private void smoothen() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double average = 0.0;
				double times = 0.0;

				if (x - 1 >= 0)
					average += particleMap[y][x - 1];
				times += 1;
				if (x + 1 < width - 1)
					average += particleMap[y][x + 1];
				times += 1;
				if (y - 1 >= 0)
					average += particleMap[y - 1][x];
				times += 1;
				if (y + 1 < height - 1)
					average += particleMap[y + 1][x];
				times += 1;

				if (x - 1 >= 0 && y - 1 >= 0)
					average += particleMap[y - 1][x - 1];
				times += 1;
				if (x + 1 < width && y - 1 >= 0)
					average += particleMap[y - 1][x + 1];
				times += 1;
				if (x - 1 >= 0 && y + 1 < height)
					average += particleMap[y + 1][x - 1];
				times += 1;
				if (x + 1 < width && y + 1 < height)
					average += particleMap[y + 1][x + 1];
				times += 1;

				average += particleMap[y][x];
				times += 1;

				average /= times;

				particleMap[y][x] = (int) average;
			}
		}
	}
}
