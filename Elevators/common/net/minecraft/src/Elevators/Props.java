package net.minecraft.src.Elevators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class Props {
	private String fileName;
	private List lines = new ArrayList();
	private Map props = new HashMap();

	public Props(String var1) {
		this.fileName = var1;
		File var2 = new File(this.fileName);

		if (var2.exists()) {
			try {
				this.load();
			} catch (IOException var4) {
				System.out.println("[Props] Unable to load " + this.fileName
						+ "!");
			}
		} else {
			this.save();
		}
	}

	public void load() throws IOException {
		BufferedReader var1 = new BufferedReader(new InputStreamReader(
				new FileInputStream(this.fileName), "UTF8"));
		this.lines.clear();
		this.props.clear();
		String var2;

		while ((var2 = var1.readLine()) != null) {
			var2 = new String(var2.getBytes(), "UTF-8");
			boolean var3 = false;
			int var4;
			char var12 = 0;

			for (var4 = 0; var4 < var2.length()
					&& Character.isWhitespace(var12 = var2.charAt(var4)); ++var4) {
				;
			}

			if (var2.length() - var4 != 0 && var2.charAt(var4) != 35
					&& var2.charAt(var4) != 33) {
				boolean var6 = var2.indexOf(92, var4) != -1;
				StringBuffer var7 = var6 ? new StringBuffer() : null;

				if (var7 != null) {
					while (var4 < var2.length()
							&& !Character.isWhitespace(var12 = var2
									.charAt(var4++)) && var12 != 61
							&& var12 != 58) {
						if (var6 && var12 == 92) {
							if (var4 == var2.length()) {
								var2 = var1.readLine();

								if (var2 == null) {
									var2 = "";
								}

								var4 = 0;

								while (true) {
									++var4;

									if (var4 >= var2.length()
											|| !Character
													.isWhitespace(var12 = var2
															.charAt(var4))) {
										break;
									}
								}
							} else {
								var12 = var2.charAt(var4++);
							}
						} else {
							switch (var12) {
							case 110:
								var7.append('\n');
								break;

							case 111:
							case 112:
							case 113:
							case 115:
							default:
								var7.append('\u0000');
								break;

							case 114:
								var7.append('\r');
								break;

							case 116:
								var7.append('\t');
								break;

							case 117:
								if (var4 + 4 <= var2.length()) {
									char var8 = (char) Integer.parseInt(
											var2.substring(var4, var4 + 4), 16);
									var7.append(var8);
									var4 += 4;
								}
							}
						}
					}
				}

				boolean var13 = var12 == 58 || var12 == 61;
				String var9;

				if (var6) {
					var9 = var7.toString();
				} else if (!var13 && !Character.isWhitespace(var12)) {
					var9 = var2.substring(var4, var4);
				} else {
					var9 = var2.substring(var4, var4 - 1);
				}

				while (var4 < var2.length()
						&& Character.isWhitespace(var12 = var2.charAt(var4))) {
					++var4;
				}

				if (!var13 && (var12 == 58 || var12 == 61)) {
					++var4;

					while (var4 < var2.length()
							&& Character.isWhitespace(var2.charAt(var4))) {
						++var4;
					}
				}

				if (!var6) {
					this.lines.add(var2);
				} else {
					StringBuilder var10 = new StringBuilder(var2.length()
							- var4);

					while (var4 < var2.length()) {
						var12 = var2.charAt(var4++);

						if (var12 == 92) {
							if (var4 == var2.length()) {
								var2 = var1.readLine();

								if (var2 == null) {
									break;
								}

								for (var4 = 0; var4 < var2.length()
										&& Character.isWhitespace(var2
												.charAt(var4)); ++var4) {
									;
								}

								var10.ensureCapacity(var2.length() - var4
										+ var10.length());
								continue;
							}

							var12 = var2.charAt(var4++);

							switch (var12) {
							case 110:
								var10.append('\n');
								break;

							case 111:
							case 112:
							case 113:
							case 115:
							default:
								var10.append('\u0000');
								break;

							case 114:
								var10.append('\r');
								break;

							case 116:
								var10.append('\t');
								break;

							case 117:
								if (var4 + 4 > var2.length()) {
									continue;
								}

								char var11 = (char) Integer.parseInt(
										var2.substring(var4, var4 + 4), 16);
								var10.append(var11);
								var4 += 4;
							}
						}

						var10.append('\u0000');
					}

					this.lines.add(var9 + "=" + var10.toString());
				}
			} else {
				this.lines.add(var2);
			}
		}

		var1.close();
	}

	public void save() {
		FileOutputStream var1 = null;

		try {
			var1 = new FileOutputStream(this.fileName);
		} catch (FileNotFoundException var11) {
			System.out.println("[Props] Unable to open " + this.fileName + "!");
		}

		PrintStream var2 = null;

		try {
			var2 = new PrintStream(var1, true, "UTF-8");
		} catch (UnsupportedEncodingException var10) {
			System.out.println("[Props] Unable to write to " + this.fileName
					+ "!");
		}

		ArrayList var3 = new ArrayList();
		Iterator var4 = this.lines.iterator();

		while (var4.hasNext()) {
			String var5 = (String) var4.next();

			if (var5.trim().length() == 0) {
				var2.println(var5);
			} else if (var5.charAt(0) == 35) {
				var2.println(var5);
			} else if (var5.contains("=")) {
				int var6 = var5.indexOf(61);
				String var7 = var5.substring(0, var6).trim();

				if (this.props.containsKey(var7)) {
					String var8 = (String) this.props.get(var7);
					var2.println(var7 + "=" + var8);
					var3.add(var7);
				} else {
					var2.println(var5);
				}
			} else {
				var2.println(var5);
			}
		}

		var4 = this.props.entrySet().iterator();

		while (var4.hasNext()) {
			Entry var12 = (Entry) var4.next();

			if (!var3.contains(var12.getKey())) {
				var2.println((String) var12.getKey() + "="
						+ (String) var12.getValue());
			}
		}

		var2.close();

		try {
			this.props.clear();
			this.lines.clear();
			this.load();
		} catch (IOException var9) {
			System.out.println("[Props] Unable to load " + this.fileName + "!");
		}
	}

	public Map returnMap() throws Exception {
		HashMap var1 = new HashMap();
		BufferedReader var2 = new BufferedReader(new FileReader(this.fileName));
		String var3;

		while ((var3 = var2.readLine()) != null) {
			if (var3.trim().length() != 0 && var3.charAt(0) != 35
					&& var3.contains("=")) {
				int var4 = var3.indexOf(61);
				String var5 = var3.substring(0, var4).trim();
				String var6 = var3.substring(var4 + 1).trim();
				var1.put(var5, var6);
			}
		}

		var2.close();
		return var1;
	}

	public boolean containsKey(String var1) {
		Iterator var2 = this.lines.iterator();

		while (var2.hasNext()) {
			String var3 = (String) var2.next();

			if (var3.trim().length() != 0 && var3.charAt(0) != 35
					&& var3.contains("=")) {
				int var4 = var3.indexOf(61);
				String var5 = var3.substring(0, var4);

				if (var5.equals(var1)) {
					return true;
				}
			}
		}

		return false;
	}

	public String getProperty(String var1) {
		Iterator var2 = this.lines.iterator();

		while (var2.hasNext()) {
			String var3 = (String) var2.next();

			if (var3.trim().length() != 0 && var3.charAt(0) != 35
					&& var3.contains("=")) {
				int var4 = var3.indexOf(61);
				String var5 = var3.substring(0, var4).trim();
				String var6 = var3.substring(var4 + 1);

				if (var5.equals(var1)) {
					return var6;
				}
			}
		}

		return "";
	}

	public void removeKey(String var1) {
		Boolean var2 = Boolean.valueOf(false);

		if (this.props.containsKey(var1)) {
			this.props.remove(var1);
			var2 = Boolean.valueOf(true);
		}

		try {
			for (int var3 = 0; var3 < this.lines.size(); ++var3) {
				String var4 = (String) this.lines.get(var3);

				if (var4.trim().length() != 0 && var4.charAt(0) != 35
						&& var4.contains("=")) {
					int var5 = var4.indexOf(61);
					String var6 = var4.substring(0, var5).trim();

					if (var6.equals(var1)) {
						this.lines.remove(var3);
						var2 = Boolean.valueOf(true);
					}
				}
			}
		} catch (ConcurrentModificationException var7) {
			this.removeKey(var1);
			return;
		}

		if (var2.booleanValue()) {
			this.save();
		}
	}

	public boolean keyExists(String var1) {
		try {
			return this.containsKey(var1);
		} catch (Exception var3) {
			return false;
		}
	}

	public String getString(String var1) {
		return this.containsKey(var1) ? this.getProperty(var1) : "";
	}

	public String getString(String var1, String var2) {
		if (this.containsKey(var1)) {
			return this.getProperty(var1);
		} else {
			this.setString(var1, var2);
			return var2;
		}
	}

	public void setString(String var1, String var2) {
		this.props.put(var1, var2);
		this.save();
	}

	public int getInt(String var1) {
		return this.containsKey(var1) ? Integer
				.parseInt(this.getProperty(var1)) : 0;
	}

	public int getInt(String var1, int var2) {
		if (this.containsKey(var1)) {
			return Integer.parseInt(this.getProperty(var1));
		} else {
			this.setInt(var1, var2);
			return var2;
		}
	}

	public void setInt(String var1, int var2) {
		this.props.put(var1, String.valueOf(var2));
		this.save();
	}

	public double getDouble(String var1) {
		return this.containsKey(var1) ? Double.parseDouble(this
				.getProperty(var1)) : 0.0D;
	}

	public double getDouble(String var1, double var2) {
		if (this.containsKey(var1)) {
			return Double.parseDouble(this.getProperty(var1));
		} else {
			this.setDouble(var1, var2);
			return var2;
		}
	}

	public void setDouble(String var1, double var2) {
		this.props.put(var1, String.valueOf(var2));
		this.save();
	}

	public long getLong(String var1) {
		return this.containsKey(var1) ? Long.parseLong(this.getProperty(var1))
				: 0L;
	}

	public long getLong(String var1, long var2) {
		if (this.containsKey(var1)) {
			return Long.parseLong(this.getProperty(var1));
		} else {
			this.setLong(var1, var2);
			return var2;
		}
	}

	public void setLong(String var1, long var2) {
		this.props.put(var1, String.valueOf(var2));
		this.save();
	}

	public boolean getBoolean(String var1) {
		return this.containsKey(var1) ? Boolean.parseBoolean(this
				.getProperty(var1)) : false;
	}

	public boolean getBoolean(String var1, boolean var2) {
		if (this.containsKey(var1)) {
			return Boolean.parseBoolean(this.getProperty(var1));
		} else {
			this.setBoolean(var1, var2);
			return var2;
		}
	}

	public void setBoolean(String var1, boolean var2) {
		this.props.put(var1, String.valueOf(var2));
		this.save();
	}
}
