import java.util.Scanner;

public class Program {
	private static final int MAX_STUDENTS = 10;
	private static final int MAX_CLASSES = 10;
	private static final int MAX_COLUMNS = 50;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		try {
			String[] students = new String[MAX_STUDENTS];
			int studentsCount = readStudents(sc, students);

			if (studentsCount == 0) {
				throw new IllegalArgumentException("No students provided.");
			}

			int[] classHours = new int[MAX_CLASSES];
			int[] classDays = new int[MAX_CLASSES];
			String[] classDayNames = new String[MAX_CLASSES];
			int classesCount = readClasses(sc, classHours, classDays, classDayNames);

			if (classesCount == 0) {
				throw new IllegalArgumentException("No classes provided.");
			}

			int[] colHour = new int[MAX_COLUMNS];
			int[] colDay = new int[MAX_COLUMNS];
			String[] colDayName = new String[MAX_COLUMNS];
			int columnsCount = buildColumns(classesCount, classHours, classDays, classDayNames, colHour, colDay, colDayName);

			if (columnsCount == 0) {
				throw new IllegalArgumentException("No class dates generated - check class definitions");
			}

			int[][] attendance = new int[MAX_STUDENTS][MAX_COLUMNS];
			readAttendance(sc, students, studentsCount, colHour, colDay, columnsCount, attendance);

			printTable(students, studentsCount, colHour, colDay, colDayName, columnsCount, attendance);
		} catch (Exception e) {
			System.err.println("Error while processing input: " + e.getMessage());
		} finally {
			sc.close();
		}
	
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
				throw new IllegalArgumentException("Unexpected end of input while reading classes");
			}
			String dayName = sc.next();

			if (count < MAX_CLASSES) {
				try {
					int hour = Integer.parseInt(first);
					classHours[count] = hour;
				} catch (NumberFormatException nfe) {
					throw new IllegalArgumentException("Invalid hour token while reading classes: '" + first + "'", nfe);
				}

				int dayIdx = dayToIndex(dayName);
				classDays[count] = dayIdx;
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

			int hour;
			int day;
			try {
				if (!sc.hasNextInt()) {
					throw new IllegalArgumentException("Invalid or missing hour for student '" + name + "'");
				}
				hour = sc.nextInt();

				if (!sc.hasNextInt()) {
					throw new IllegalArgumentException("Invalid or missing day for student '" + name + "'");
				}
				day = sc.nextInt();
			} catch (Exception e) {
				throw new IllegalArgumentException("Error reading hour/day for '" + name + "': " + e.getMessage(), e);
			}

			String status = null;
			if (sc.hasNext()) {
				status = sc.next();
			} else {
				throw new IllegalArgumentException("Missing status for '" + name + "'");
			}

			if (!status.equals("HERE") && !status.equals("NOT_HERE")) {
				throw new IllegalArgumentException("Invalid status token for '" + name + "': '" + status + "' (expected HERE or NOT_HERE)");
			}

			int studentIndex = findStudent(students, studentsCount, name);
			int columnIndex = findColumn(colHour, colDay, columnsCount, hour, day);

			if (studentIndex != -1 && columnIndex != -1) {
				if (status.equals("HERE")) {
					attendance[studentIndex][columnIndex] = 1;
				} else {
					attendance[studentIndex][columnIndex] = -1;
				}
			} else {
				if (studentIndex == -1) {
					throw new IllegalArgumentException("Unknown student in attendance record: '" + name + "'");
				}
				if (columnIndex == -1) {
					throw new IllegalArgumentException("No matching class column for " + hour + ":00 on day " + day + " (student '" + name + "')");
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
		switch (dayIndex) {
			case 1: return 7;
			case 2: return 1;
			case 3: return 2;
			case 4: return 3;
			case 5: return 4;
			case 6: return 5;
			default: return 6;
		}
	}

	private static int dayToIndex(String day) {
		if (day == null) {
			throw new IllegalArgumentException("Day name is null");
		}
		switch (day) {
			case "MO": return 1;
			case "TU": return 2;
			case "WE": return 3;
			case "TH": return 4;
			case "FR": return 5;
			case "SA": return 6;
			case "SU": return 7;
			default:
				throw new IllegalArgumentException("Invalid day name: '" + day + "' (expected MO/TU/WE/TH/FR/SA/SU)");
		}
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
		String s = hour + ":00 " + dayName + " ";
		if (day < 10) {
			s += " ";
		}
		s += day;

		if (s.length() > 10) {
			System.out.print(s.substring(0, 10));
		} else {
			System.out.print(s);
			for (int i = s.length(); i < 10; i++) {
				System.out.print(" ");
			}
		}
	}

	private static void printNameCell(String name) {
		char[] chars = name.toCharArray();
		int toPrint = Math.min(chars.length, 10);
		for (int i = 0; i < toPrint; i++) {
			System.out.print(chars[i]);
		}
		for (int i = toPrint; i < 10; i++) {
			System.out.print(" ");
		}
	}

	private static void printAttendanceCell(int value) {
		String s;
		switch (value) {
			case 1: s = "1"; break;
			case -1: s = "-1"; break;
			default: s = ""; break;
		}

		if (s.length() > 10) {
			System.out.print(s.substring(0, 10));
		} else {
			for (int i = 0; i < 10 - s.length(); i++) {
				System.out.print(" ");
			}
			System.out.print(s);
		}
	}
}
