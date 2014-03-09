import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IslandGenerator {
	int height;
	int width;
	double[][] map;
	double[][] particleMap;

	public double[][] getMap(){
		return map;
	}
	
	public IslandGenerator(int inWid, int inHei) {
		width = inWid;
		height = inHei;
		map = new double[height][width];
		particleMap = new double[height][width];
	}

//	@SuppressWarnings("static-access")
	public void generateIsland(int inWid, int inHei, int inFreq, int inOct) {
		// generator noise
		PerlinNoise png = new PerlinNoise(height, width);
		Random random = new Random();
		
		map = png.generate_noise(inWid, inHei, inFreq, inOct);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				particleMap[y][x] = 0.0;//(int) (((png.noise(x, y) + 1) * .5) * 255);
			}
		}

		
		for (int q = 0; q < height; q++) {
			for (int r = 0; r < width; r++) {
				System.out.println(r + "," + q);
				particleMap[q][r] = 0;
				
				// not sure
				for (int i = 0; i < (height * width) * .85; i++) {
					int x = 15 + random.nextInt(width - 31);// rand from 15 to width-16
					int y = 15 + random.nextInt(height - 31);// rand from 15 to width-16

					for (int j = 0; j < (height * width) * .05; j++) {
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
								{
									choices.add("down");
									oneTrue = true;
								}

								if (oneTrue)// one of the 4 if statements above
									break;

								int randInt = random.nextInt(choices.size());
								String newString = choices.get(randInt);
								if (newString == "left")
									x -= 1;
								if (newString == "right")
									x += 1;
								if (newString == "up")
									y -= 1;
								if (newString == "down")
									y += 1;
							}

					}
				}
			}
		}

		
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				map[y][x] *= ((particleMap[y][x]) / 255.0);
			}
		}
		
//		smoothen();
		
		//return map maybe
	}

	private void smoothen() {
		for (int y =0; y< height;y++){
            for(int x = 0; x < width; x++){
                double average = 0.0;
                double times = 0.0;

                if (x - 1 >= 0)
                    average += map[y][x-1];
                    times += 1;
                if( x + 1 < width-1)
                    average += map[y][x+1];
                    times += 1;
                if( y - 1 >= 0)
                    average += map[y-1][x];
                    times += 1;
                if (y + 1 < height-1)
                    average += map[y+1][x];
                    times += 1;

                if (x - 1 >= 0 && y - 1 >= 0)
                    average += map[y-1][x-1];
                    times += 1;
                if (x + 1 < width && y - 1 >= 0)
                    average += map[y-1][x+1];
                    times += 1;
                if( x - 1 >= 0 && y + 1 < height)
                    average += map[y+1][x-1];
                    times += 1;
                if (x + 1 < width && y + 1 < height)
                    average += map[y+1][x+1];
                    times += 1;

                average += map[y][x];
                times += 1;

                average /= times;

                map[y][x] = (int)average;
	}
}
	}
}