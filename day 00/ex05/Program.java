import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		private static final int MAX_STUDENTS = 10;
		private static final int MAX_CLASSES = 10;
		private static final int MAX_COLUMNS = 50;

		Scanner sc = new Scanner(System.in);

		String[] students = new String[MAX_STUDENTS];
		int studentsCount = readStudents(sc, students);

		int[] classHours = new int[MAX_CLASSES];
		int[] classDays = new int[MAX_CLASSES];
		String[] classDayNames = new String[MAX_CLASSES];
		int classesCount = readClasses(sc, classHours, classDays, classDayNames);

		int[] colHour = new int[MAX_COLUMNS];
		int[] colDay = new int[MAX_COLUMNS];
		String[] colDayName = new String[MAX_COLUMNS];
		int columnsCount = buildColumns(classesCount, classHours, classDays, classDayNames, colHour, colDay, colDayName);

		int[][] attendance = new int[MAX_STUDENTS][MAX_COLUMNS];
		readAttendance(sc, students, studentsCount, colHour, colDay, columnsCount, attendance);

		printTable(students, studentsCount, colHour, colDay, colDayName, columnsCount, attendance);
		sc.close();
	
	}


	private static int readStudents(Scanner sc, String[] students) {
		int count = 0;
		while (sc.hasNext()) {
			String token = sc.next();
			if (token.equals(".")) {
				break;
			}
			if (count < MAX_STUDENTS) {
				students[count] = token;
				count++;
			}
		}
		return count;
	}

	private static int readClasses(Scanner sc, int[] classHours, int[] classDays, String[] classDayNames) {
		int count = 0;
		while (sc.hasNext()) {
			String first = sc.next();
			if (first.equals(".")) {
				break;
			}
			if (!sc.hasNext()) {
				break;
			}
			String dayName = sc.next();

			if (count < MAX_CLASSES) {
				classHours[count] = parsePositiveInt(first);
				classDays[count] = dayToIndex(dayName);
				classDayNames[count] = dayName;
				count++;
			}
		}
		return count;
	}

	private static void readAttendance(
		Scanner sc,
		String[] students,
		int studentsCount,
		int[] colHour,
		int[] colDay,
		int columnsCount,
		int[][] attendance
	) {
		while (sc.hasNext()) {
			String name = sc.next();
			if (name.equals(".")) {
				break;
			}

			int hour = sc.nextInt();
			int day = sc.nextInt();
			String status = sc.next();

			int studentIndex = findStudent(students, studentsCount, name);
			int columnIndex = findColumn(colHour, colDay, columnsCount, hour, day);

			if (studentIndex != -1 && columnIndex != -1) {
				if (status.equals("HERE")) {
					attendance[studentIndex][columnIndex] = 1;
				} else {
					attendance[studentIndex][columnIndex] = -1;
				}
			}
		}
	}

	private static int buildColumns(
		int classesCount,
		int[] classHours,
		int[] classDays,
		String[] classDayNames,
		int[] colHour,
		int[] colDay,
		String[] colDayName
	) {
		int count = 0;
		for (int i = 0; i < classesCount; i++) {
			int firstDate = firstDateInSeptember2020(classDays[i]);
			for (int d = firstDate; d <= 30; d += 7) {
				if (count < MAX_COLUMNS) {
					colHour[count] = classHours[i];
					colDay[count] = d;
					colDayName[count] = classDayNames[i];
					count++;
				}
			}
		}

		sortColumns(count, colHour, colDay, colDayName);
		return count;
	}

	private static void sortColumns(int n, int[] colHour, int[] colDay, String[] colDayName) {
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				boolean swap = false;
				if (colDay[j] > colDay[j + 1]) {
					swap = true;
				} else if (colDay[j] == colDay[j + 1] && colHour[j] > colHour[j + 1]) {
					swap = true;
				}

				if (swap) {
					int tmpHour = colHour[j];
					colHour[j] = colHour[j + 1];
					colHour[j + 1] = tmpHour;

					int tmpDay = colDay[j];
					colDay[j] = colDay[j + 1];
					colDay[j + 1] = tmpDay;

					String tmpName = colDayName[j];
					colDayName[j] = colDayName[j + 1];
					colDayName[j + 1] = tmpName;
				}
			}
		}
	}

	private static int firstDateInSeptember2020(int dayIndex) {
		if (dayIndex == 1) {
			return 7;
		}
		if (dayIndex == 2) {
			return 1;
		}
		if (dayIndex == 3) {
			return 2;
		}
		if (dayIndex == 4) {
			return 3;
		}
		if (dayIndex == 5) {
			return 4;
		}
		if (dayIndex == 6) {
			return 5;
		}
		return 6;
	}

	private static int dayToIndex(String day) {
		if (day.equals("MO")) {
			return 1;
		}
		if (day.equals("TU")) {
			return 2;
		}
		if (day.equals("WE")) {
			return 3;
		}
		if (day.equals("TH")) {
			return 4;
		}
		if (day.equals("FR")) {
			return 5;
		}
		if (day.equals("SA")) {
			return 6;
		}
		return 7;
	}

	private static int parsePositiveInt(String s) {
		char[] chars = s.toCharArray();
		int num = 0;
		for (int i = 0; i < chars.length; i++) {
			num = num * 10 + (chars[i] - '0');
		}
		return num;
	}

	private static int findStudent(String[] students, int studentsCount, String name) {
		for (int i = 0; i < studentsCount; i++) {
			if (students[i].equals(name)) {
				return i;
			}
		}
		return -1;
	}

	private static int findColumn(int[] colHour, int[] colDay, int columnsCount, int hour, int day) {
		for (int i = 0; i < columnsCount; i++) {
			if (colHour[i] == hour && colDay[i] == day) {
				return i;
			}
		}
		return -1;
	}

	private static void printTable(
		String[] students,
		int studentsCount,
		int[] colHour,
		int[] colDay,
		String[] colDayName,
		int columnsCount,
		int[][] attendance
	) {
		printHeader(colHour, colDay, colDayName, columnsCount);
		for (int i = 0; i < studentsCount; i++) {
			printNameCell(students[i]);
			for (int j = 0; j < columnsCount; j++) {
				System.out.print("|");
				printAttendanceCell(attendance[i][j]);
			}
			System.out.println("|");
		}
	}

	private static void printHeader(int[] colHour, int[] colDay, String[] colDayName, int columnsCount) {
		for (int i = 0; i < 10; i++) {
			System.out.print(" ");
		}
		for (int i = 0; i < columnsCount; i++) {
			System.out.print("|");
			printHeaderCell(colHour[i], colDayName[i], colDay[i]);
		}
		System.out.println("|");
	}

	private static void printHeaderCell(int hour, String dayName, int day) {
		System.out.print(hour);
		System.out.print(":00 ");
		System.out.print(dayName);
		System.out.print(" ");
		if (day < 10) {
			System.out.print(" ");
		}
		System.out.print(day);
	}

	private static void printNameCell(String name) {
		char[] chars = name.toCharArray();
		if (chars.length >= 2) {
			System.out.print(chars[0]);
			System.out.print(chars[1]);
		} else if (chars.length == 1) {
			System.out.print(chars[0]);
		}

		int printed = chars.length >= 2 ? 2 : chars.length;
		for (int i = printed; i < 10; i++) {
			System.out.print(" ");
		}
	}

	private static void printAttendanceCell(int value) {
		for (int i = 0; i < 9; i++) {
			System.out.print(" ");
		}
		if (value == 1) {
			System.out.print("1");
		} else if (value == -1) {
			System.out.print("-1");
		} else {
			System.out.print(" ");
		}
	}
}
