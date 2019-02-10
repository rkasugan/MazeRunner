public class Wall {

	private String side;
	private int loc;
	private int topLeftX, topLeftY;
	private int topRightX, topRightY;
	private int botLeftX, botLeftY;
	private int botRightX, botRightY;
	private int triangle1X, triangle1Y, triangle2X, triangle2Y, triangle3X, triangle3Y, triangle4X, triangle4Y, triangle5X, triangle5Y, triangle6X, triangle6Y;

	public Wall(String side, int loc) {
		this.side = side;
		this.loc = loc;

		if (side.equals("left") || side.equals("leftEmpty")) {
			topLeftX = 0;
			topLeftY = 0;
			topRightX = 80;
			topRightY = 50;
			botRightX = 80;
			botRightY = 300;
			botLeftX = 0;
			botLeftY = 350;
			int x = 80;
			int y = 350;
			int z = 50;
			int y2 = 250;

			for (int i = 1; i < loc; i ++) {
				topLeftX = topRightX;
				topLeftY = topRightY; //top left is good
				x/=2;
				topRightX = topLeftX + x;
				z/=2;
				topRightY = topLeftY + z; //top right is good
				botRightX = topRightX;
				y = y2;
				y2 = y-(2*z);
				botRightY = topRightY + y2;
				botLeftX = topLeftX;
				botLeftY = botRightY + z;
			}

			if (side.equals("leftEmpty")) {
				triangle1X = topLeftX;        //the top 3 triangle points
				triangle1Y = topLeftY;
				triangle2X = triangle1X;
				triangle2Y = triangle1Y + z;
				triangle3X = triangle2X + x;
				triangle3Y = triangle2Y;

				triangle4X = botLeftX;        //the bottom 3 triangle points
				triangle4Y = botLeftY;
				triangle5X = triangle4X;
				triangle5Y = triangle4Y - z;
				triangle6X = triangle5X + x;
				triangle6Y = triangle5Y;


				topLeftX = triangle2X;        //sets the middle rectangle
				topLeftY = triangle2Y;
				botLeftX = triangle5X;
				botLeftY = triangle5Y;
			}
		}
		else if (side.equals("right") || side.equals("rightEmpty")) {
			topLeftX = 240;
			topLeftY = 50;
			topRightX = 320;
			topRightY = 0;
			botRightX = 320;
			botRightY = 350;
			botLeftX = 240;
			botLeftY = 300;
			int x = 80;
			int y = 350;
			int z = 50;
			int y2 = 250;

			for (int i = 1; i < loc; i ++) {
				topRightX = topLeftX;
				topRightY = topLeftY;
				x/=2;
				topLeftX = topRightX - x;
				z/=2;
				topLeftY = topRightY + z;
				botLeftX = topLeftX;
				y = y2;
				y2 = y-(2*z);
				botLeftY = topLeftY + y2;
				botRightX = topRightX;
				botRightY = botLeftY + z;
			}

			if (side.equals("rightEmpty")) {
				triangle1X = topRightX;        //the top 3 triangle points
				triangle1Y = topRightY;
				triangle2X = triangle1X;
				triangle2Y = triangle1Y + z;
				triangle3X = triangle2X - x;
				triangle3Y = triangle2Y;

				triangle4X = botRightX;        //the bottom 3 triangle points
				triangle4Y = botRightY;
				triangle5X = triangle4X;
				triangle5Y = triangle4Y - z;
				triangle6X = triangle5X - x;
				triangle6Y = triangle5Y;


				topRightX = triangle2X;        //sets the middle rectangle
				topRightY = triangle2Y;
				botRightX = triangle5X;
				botRightY = triangle5Y;
			}
		}
		else if (side.equals("top")) {
			topLeftX = 0;
			topLeftY = 0;
			topRightX = 320;
			topRightY = 0;
			botRightX = 240;
			botRightY = 50;
			botLeftX = 80;
			botLeftY = 50;
			int x = 80;
			int z = 50;

			for (int i = 1; i < loc; i ++) {
				topLeftX = botLeftX;
				topLeftY = botLeftY;
				topRightX = botRightX;
				topRightY = botRightY;
				x/=2;
				botLeftX = topLeftX + x;
				z/=2;
				botLeftY = topLeftY + z;
				botRightX = topRightX - x;
				botRightY = botLeftY;

			}
		}
		else if (side.equals("bot")) {
			topLeftX = 80;
			topLeftY = 300;
			topRightX = 240;
			topRightY = 300;
			botRightX = 320;
			botRightY = 350;
			botLeftX = 0;
			botLeftY = 350;
			int x = 80;
			int z = 50;

			for (int i = 1; i < loc; i ++) {
				botLeftX = topLeftX;
				botLeftY = topLeftY;
				botRightX = topRightX;
				botRightY = topRightY;
				x/=2;
				z/=2;
				topLeftX = botLeftX + x;
				topLeftY = botLeftY - z;
				topRightX = botRightX - x;
				topRightY = botRightY - z;
			}

		}
		else if (side.equals("forward")) {
			topLeftX = 0;
			topLeftY = 0;
			topRightX = 80;
			topRightY = 50;
			botRightX = 80;
			botRightY = 300;
			botLeftX = 0;
			botLeftY = 350;
			int x = 80;
			int y = 350;
			int z = 50;
			int y2 = 250;

			for (int i = 1; i < loc-1; i ++) {
				topLeftX = topRightX;
				topLeftY = topRightY; //top left is good
				x/=2;
				topRightX = topLeftX + x;
				z/=2;
				topRightY = topLeftY + z; //top right is good
				botRightX = topRightX;
				y = y2;
				y2 = y-(2*z);
				botRightY = topRightY + y2;
				botLeftX = topLeftX;
				botLeftY = botRightY + z;
			} //calculate all the info of the wall on the right side right before the forward-facing wall

			topLeftX = topRightX;
			topLeftY = topRightY;
			topRightX = topLeftX + (2*x);
			if (loc == 6) {       //program finds the middle wall by calculating the coordinates of the left wall right before it, and adding some to it
				topRightX += 2;   //however, since java cuts decimals, the program loses space that should have added up on the right side
			}					  //which results in the forward wall not being long enough on the right side, so I add some extra space to it
			else if (loc > 6) {
				topRightX += 4;
			}
			topRightY = topLeftY;
			botLeftX = topLeftX;
			botLeftY = topLeftY + y2;
			botRightX = topRightX;
			botRightY = botLeftY;
		}
	}

	public String toString() {
		return "Side: " + side + "; Loc: " + loc + "\ntopLeft: (" + topLeftX + ", " + topLeftY + ") -- topRight: (" + topRightX + ", " + topRightY + ") -- botRight: (" + botRightX + ", " + botRightY + ") -- botLeft: (" + botLeftX + ", " + botLeftY + ")";
	}
	public int getTopRightX() {
		return topRightX;
	}
	public int getTopRightY() {
		return topRightY;
	}
	public int getTopLeftX() {
		return topLeftX;
	}
	public int getTopLeftY() {
		return topLeftY;
	}
	public int getBotLeftX() {
		return botLeftX;
	}
	public int getBotLeftY() {
		return botLeftY;
	}
	public int getBotRightX() {
		return botRightX;
	}
	public int getBotRightY() {
		return botRightY;
	}
	public String getSide() {
		return side;
	}
	public int[] getTriangleXTop() {
		int triangleX[] = {triangle1X, triangle2X, triangle3X};
		return triangleX;
	}
	public int[] getTriangleXBot() {
		int triangleX[] = {triangle4X, triangle5X, triangle6X};
		return triangleX;
	}
	public int[] getTriangleYTop() {
		int triangleY[] = {triangle1Y, triangle2Y, triangle3Y};
		return triangleY;
	}
	public int[] getTriangleYBot() {
		int triangleY[] = {triangle4Y, triangle5Y, triangle6Y};
		return triangleY;
	}


}