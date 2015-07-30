import java.util.*;

public class Simulation {
	public static void main(String[] args) {
		ArrayList<String> information_list = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		String str, str1;
		char ch[];
		System.out.println("Please enter the data: ");
		while (scan.hasNext()) {
			str = scan.nextLine();
			ch = str.toCharArray();
			for (int in = 0; in < ch.length; in++) {
				str1 = String.valueOf(ch[in]);
				information_list.add(str1);
			}
			information_list.add("\n");
		}

		System.out.println("\n-----------------------------------");
		// for (String i : information_list) {
		// System.out.print(i);
		// }

		int count = 0, limit = information_list.size(), trigger = 0;
		String tempstring = "";
		AvailableTransmission[] available_transmission = new AvailableTransmission[1000];
		Flows[] flows = new Flows[1000];
		for (int j = 0; j < 1000; j++) {
			available_transmission[j] = new AvailableTransmission();
			flows[j] = new Flows();
		}
		int i = 0, atI = 0, fI = 0;
		while (i < limit) {
			if (information_list.get(i).equals("#") == true) {
				if (information_list.get(i + 2).equals("S") == true) {
					count = 0;
					trigger = 0;
				} else if (information_list.get(i + 2).equals("T") == true) {
					count = 3;
					trigger = 1;
				}

				System.out.print(information_list.get(i));
				i++;
				while (i < limit) {
					if (information_list.get(i).equals("\n") == true) {
						System.out.print(information_list.get(i));
						i++;
						break;
					} else
						System.out.print(information_list.get(i));
					i++;
				}
			} else {
				while (i < limit
						&& information_list.get(i).equals("\n") != true) {
					while (i < limit
							&& information_list.get(i).equals(" ") != true
							&& information_list.get(i).equals("\t") != true
							&& information_list.get(i).equals("\n") != true) {
						tempstring = tempstring.concat(information_list.get(i));
						i++;
					}
					System.out.print(tempstring);
					if (tempstring.equals("") != true) {
						if (count == 0)
							available_transmission[atI].Source = tempstring;
						else if (count == 1)
							available_transmission[atI].Destination = tempstring;
						else if (count == 2) {
							available_transmission[atI].Capacity = tempstring;
							if (available_transmission[atI].Capacity
									.indexOf('b') == -1) {
								System.out
										.println("\nError input!!! Please ty again!!!");
								return;
							}
						}

						else if (count == 3)
							flows[fI].Time = tempstring;
						else if (count == 4)
							flows[fI].Source = tempstring;
						else if (count == 5)
							flows[fI].Destination = tempstring;
						else if (count == 6)
							flows[fI].Style = tempstring;
						else if (count == 7) {
							flows[fI].Capacity = tempstring;
							if (flows[fI].Capacity.indexOf('b') == -1) {
								System.out
										.println("\nError input!!! Please try again!!!");
								return;
							}
						}

						else if (count == 8)
							flows[fI].Duration = tempstring;
						count++;
					}
					tempstring = "";
					if (i < limit
							&& (information_list.get(i).equals(" ") == true || information_list
									.get(i).equals("\t") == true)) {
						System.out.print(information_list.get(i));
						i++;
					}
					if (count == 3 && trigger == 0) {
						atI++;
						count = 0;
					} else if (count == 9 && trigger == 1) {
						fI++;
						count = 3;
					} else if (i < limit && count == 8 && trigger == 1
							&& information_list.get(i).equals("\n") == true) {
						fI++;
						count = 3;
					}
				}
				System.out.print("\n");
				i++;
			}
		}

		// file information collect------complete
		int countAT = 0, countF = 0;
		while (available_transmission[countAT].Source.equals("") != true) {
			countAT++;
		}
		System.out.println("\n" + "We totally have " + countAT
				+ " available transmission!!");
		while (flows[countF].Source.equals("") != true) {
			countF++;
		}
		System.out.println("We totally have " + countF + " flows!!");
		// count the source and requirement we need to meet----complete
		int sindex, number;
		char unit;
		for (i = 0; i < countAT; i++) {
			available_transmission[i].ID = i;
			sindex = available_transmission[i].Capacity.indexOf('b');
			number = Integer.parseInt(available_transmission[i].Capacity
					.substring(0, sindex - 1));
			unit = available_transmission[i].Capacity.charAt(sindex - 1);
			if (unit == 'K')
				available_transmission[i].Capacity_ = number;
			else if (unit == 'M')
				available_transmission[i].Capacity_ = number * 1E3;
			else if (unit == 'G')
				available_transmission[i].Capacity_ = number * 1E6;
			else if (unit == 'T')
				available_transmission[i].Capacity_ = number * 1E9;
			available_transmission[i].CurrentCapacity = available_transmission[i].Capacity_;
		}
		// change the string capacity to number and consider the
		// unit------complete
		for (i = 0; i < countF; i++) {
			sindex = flows[i].Capacity.indexOf('b');
			number = Integer.parseInt(flows[i].Capacity
					.substring(0, sindex - 1));
			unit = flows[i].Capacity.charAt(sindex - 1);
			if (unit == 'K')
				flows[i].Capacity_ = number;
			else if (unit == 'M')
				flows[i].Capacity_ = number * 1E3;
			else if (unit == 'G')
				flows[i].Capacity_ = number * 1E6;
			else if (unit == 'T')
				flows[i].Capacity_ = number * 1E9;
			flows[i].Capacity_REAL = flows[i].Capacity_;
			flows[i].Time_ = Integer.parseInt(flows[i].Time);
			sindex = flows[i].Duration.indexOf('s');
			if (sindex != -1)
				flows[i].Duration_ = Integer.parseInt(flows[i].Duration
						.substring(0, sindex));
			if (flows[i].Style.equals("FBR") == true)
				flows[i].Priority = 4;
			else if (flows[i].Style.equals("VBR-RT") == true)
				flows[i].Priority = 3;
			else if (flows[i].Style.equals("VBR") == true)
				flows[i].Priority = 2;
			else if (flows[i].Style.equals("ABR") == true)
				flows[i].Priority = 1;
			else
				System.out.println("Wrong type!!!");
		}
		// Transfer string information to integer to make life easier in
		// Flows-----Complete
		int timepoint = 0;
		double singlepath = 0, multipath = 0;
		ArrayList<Flows> FBR = new ArrayList<Flows>();
		ArrayList<Flows> VBR_RT = new ArrayList<Flows>();
		ArrayList<Flows> VBR = new ArrayList<Flows>();
		ArrayList<Flows> ABR = new ArrayList<Flows>();
		ArrayList<AvailableTransmission> path;
		ArrayList<String> nodes;
		ArrayList<ArrayList<String>> nodesSet = new ArrayList<ArrayList<String>>();
		ReferenceForPass reference = new ReferenceForPass();
		AssistantMethods method = new AssistantMethods();
		ArrayList<ArrayList<AvailableTransmission>> singlepathset = new ArrayList<ArrayList<AvailableTransmission>>();
		double[] pathvalue = new double[1000];
		while (true) {
			System.out
					.println("-------------------------------------------------------------------");
			System.out.println("Start of time " + timepoint);
			method.restoreAT(available_transmission, countAT);
			for (i = 0; i < countF; i++) {
				if (flows[i].Time_ == timepoint && flows[i].Priority == 4) {
					FBR.add(flows[i]);
				} else if (flows[i].Time_ == timepoint
						&& flows[i].Priority == 3) {
					VBR_RT.add(flows[i]);
				} else if (flows[i].Time_ == timepoint
						&& flows[i].Priority == 2) {
					VBR.add(flows[i]);
				} else if (flows[i].Time_ == timepoint
						&& flows[i].Priority == 1) {
					ABR.add(flows[i]);
				}
			}
			// Sort the flows by their priority----complete
			System.out.println("Start of FBR in time " + timepoint);
			i = 0;
			while (FBR.size() != 0 && i < FBR.size()) {
				FBR.get(i).Capacity_REAL = FBR.get(i).Capacity_;
				multipath = 0;
				int index = -1;
				while (true) {
					path = method.Findway(FBR.get(i).Source,
							FBR.get(i).Destination, available_transmission,
							countAT, reference);
					if (path != null) {
						index++;
						nodes = reference.NodesetPass;
						nodesSet.add(nodes);
						singlepathset.add(path);
						singlepath = reference.doublePass;
						pathvalue[index] = singlepath;
						multipath += singlepath;
					} else
						break;
				}
				if (FBR.get(i).Capacity_REAL > multipath) {
					System.out
							.println("Warning: Cannot transfer the bit in FBR from "
									+ FBR.get(i).Source
									+ " to "
									+ FBR.get(i).Destination + "!!!");
					FBR.remove(i);
				} else {
					if (index != -1) {
						for (int k = 0; k < index + 1; k++) {
							if (pathvalue[k] >= FBR.get(i).Capacity_REAL) {
								for (int aa = 0; aa < singlepathset.get(k)
										.size(); aa++) {
									singlepathset.get(k).get(aa).CurrentCapacityREAL -= FBR
											.get(i).Capacity_REAL;
								}
								for (int aa = 0; aa < nodesSet.get(k).size(); aa++) {
									System.out.print(nodesSet.get(k).get(aa)
											+ " ");
								}
								System.out.println("\n"
										+ FBR.get(i).Capacity_REAL + "Kb");
								break;
							} else {
								for (int aa = 0; aa < nodesSet.get(k).size(); aa++) {
									System.out.print(nodesSet.get(k).get(aa)
											+ " ");
								}
								System.out.print("\n");
								for (int aa = 0; aa < singlepathset.get(k)
										.size(); aa++) {

									singlepathset.get(k).get(aa).CurrentCapacityREAL -= pathvalue[k];
								}
								FBR.get(i).Capacity_REAL -= pathvalue[k];
								System.out.println(pathvalue[k] + "Kb and...");
							}
						}
					}
					FBR.get(i).Duration_--;
					if (FBR.get(i).Duration_ == 0)
						FBR.remove(i);
				}
				method.restoreATPART(available_transmission, countAT);
				i++;
			}
			method.clearAT(singlepathset);
			method.clearAT(nodesSet);
			System.out.println("End of FBR in time " + timepoint);
			System.out.println("Start of VBR-RT in time " + timepoint);
			i = 0;
			while (VBR_RT.size() != 0 && i < VBR_RT.size()) {
				multipath = 0;
				int index = -1;
				while (true) {
					path = method.Findway(VBR_RT.get(i).Source,
							VBR_RT.get(i).Destination, available_transmission,
							countAT, reference);
					if (path != null) {
						index++;
						nodes = reference.NodesetPass;
						nodesSet.add(nodes);
						singlepathset.add(path);
						singlepath = reference.doublePass;
						pathvalue[index] = singlepath;
						multipath += singlepath;
					} else
						break;
				}
				if (VBR_RT.get(i).Capacity_REAL > multipath) {
					System.out
							.println("Warning: Cannot transfer the bit in VBR-RT from "
									+ VBR_RT.get(i).Source
									+ " to "
									+ VBR_RT.get(i).Destination + "!!!");
				} else {
					if (index != -1) {
						for (int k = 0; k < index + 1; k++) {
							if (pathvalue[k] >= VBR_RT.get(i).Capacity_REAL) {
								for (int aa = 0; aa < singlepathset.get(k)
										.size(); aa++) {
									singlepathset.get(k).get(aa).CurrentCapacityREAL -= VBR_RT
											.get(i).Capacity_REAL;
								}
								for (int aa = 0; aa < nodesSet.get(k).size(); aa++) {
									System.out.print(nodesSet.get(k).get(aa)
											+ " ");
								}
								System.out.println("\n"
										+ VBR_RT.get(i).Capacity_REAL + "Kb");
								break;
							} else {
								for (int aa = 0; aa < singlepathset.get(k)
										.size(); aa++) {

									singlepathset.get(k).get(aa).CurrentCapacityREAL -= pathvalue[k];

								}
								VBR_RT.get(i).Capacity_REAL -= pathvalue[k];
								for (int aa = 0; aa < nodesSet.get(k).size(); aa++) {
									System.out.print(nodesSet.get(k).get(aa)
											+ " ");
								}
								System.out.println(pathvalue[k] + "Kb and...");
							}
						}
					}

				}
				VBR_RT.remove(i);
				method.restoreATPART(available_transmission, countAT);
				i++;
			}
			System.out.println("End of VBR-RT in time " + timepoint);
			method.clearAT(singlepathset);
			method.clearAT(nodesSet);
			System.out.println("Start of VBR in time " + timepoint);
			i = 0;
			while (VBR.size() != 0 && i < VBR.size()) {
				multipath = 0;
				int index = -1;
				while (true) {
					path = method.Findway(VBR.get(i).Source,
							VBR.get(i).Destination, available_transmission,
							countAT, reference);
					if (path != null) {
						index++;
						nodes = reference.NodesetPass;
						nodesSet.add(nodes);
						singlepathset.add(path);
						singlepath = reference.doublePass;
						pathvalue[index] = singlepath;
						multipath += singlepath;
					} else
						break;
				}
				if (VBR.get(i).Capacity_REAL > multipath) {
					for (int bb = 0; bb < singlepathset.size(); bb++) {
						for (int cc = 0; cc < singlepathset.get(bb).size(); cc++) {
							singlepathset.get(bb).get(cc).CurrentCapacityREAL -= pathvalue[bb];
						}
					}
					VBR.get(i).Capacity_REAL -= multipath;
				} else {
					if (index != -1) {
						for (int k = 0; k < index + 1; k++) {
							if (pathvalue[k] >= VBR.get(i).Capacity_REAL) {
								for (int aa = 0; aa < singlepathset.get(k)
										.size(); aa++) {
									singlepathset.get(k).get(aa).CurrentCapacityREAL -= VBR
											.get(i).Capacity_REAL;
								}
								for (int aa = 0; aa < nodesSet.get(k).size(); aa++) {
									System.out.print(nodesSet.get(k).get(aa)
											+ " ");
								}
								System.out.println("\n"
										+ VBR.get(i).Capacity_REAL + "Kb");
								break;
							} else {
								for (int aa = 0; aa < singlepathset.get(k)
										.size(); aa++) {
									singlepathset.get(k).get(aa).CurrentCapacityREAL -= pathvalue[k];

								}
								VBR.get(i).Capacity_REAL -= pathvalue[k];
								for (int aa = 0; aa < nodesSet.get(k).size(); aa++) {
									System.out.print(nodesSet.get(k).get(aa)
											+ " ");
								}
								System.out.println(pathvalue[k] + "Kb and...");
							}
						}
					}
					VBR.remove(i);
				}

				method.restoreATPART(available_transmission, countAT);
				i++;
			}
			System.out.println("End of VBR in time " + timepoint);
			method.clearAT(singlepathset);
			method.clearAT(nodesSet);
			System.out.println("Start of ABR in time " + timepoint);
			i = 0;
			while (ABR.size() != 0 && i < ABR.size()) {
				multipath = 0;
				int index = -1;
				while (true) {
					path = method.Findway(ABR.get(i).Source,
							ABR.get(i).Destination, available_transmission,
							countAT, reference);
					if (path != null) {
						index++;
						nodes = reference.NodesetPass;
						nodesSet.add(nodes);
						singlepathset.add(path);
						singlepath = reference.doublePass;
						pathvalue[index] = singlepath;
						multipath += singlepath;
					} else
						break;
				}

				if (ABR.get(i).Capacity_REAL > multipath) {
					for (int bb = 0; bb < singlepathset.size(); bb++) {
						for (int cc = 0; cc < singlepathset.get(bb).size(); cc++) {
							singlepathset.get(bb).get(cc).CurrentCapacityREAL -= pathvalue[bb];
						}
					}
					ABR.get(i).Capacity_REAL -= multipath;
					for (int bb = 0; bb < nodesSet.size(); bb++) {
						for (int cc = 0; cc < nodesSet.get(bb).size(); cc++) {
							System.out.print(nodesSet.get(bb).get(cc) + " ");
						}
						System.out.println(pathvalue[bb] + "Kb");
					}

				} else {
					if (index != -1) {
						for (int k = 0; k < (index + 1); k++) {
							if (pathvalue[k] >= ABR.get(i).Capacity_REAL) {
								for (int aa = 0; aa < singlepathset.get(k)
										.size(); aa++) {
									singlepathset.get(k).get(aa).CurrentCapacityREAL -= ABR
											.get(i).Capacity_REAL;
								}
								for (int aa = 0; aa < nodesSet.get(k).size(); aa++) {
									System.out.print(nodesSet.get(k).get(aa)
											+ " ");
								}
								System.out.println("\n" + ABR.get(i).Capacity_REAL
										+ "Kb");
								break;
							} else {
								for (int aa = 0; aa < singlepathset.get(k)
										.size(); aa++) {
									singlepathset.get(k).get(aa).CurrentCapacityREAL -= pathvalue[k];

								}
								ABR.get(i).Capacity_REAL -= pathvalue[k];
								for (int aa = 0; aa < nodesSet.get(k).size(); aa++) {
									System.out.print(nodesSet.get(k).get(aa)
											+ " ");
								}
								System.out.println("\n" + pathvalue[k] + "Kb and...");
							}
						}
					}
					ABR.remove(i);
				}
				i++;
			}

			System.out.println("End of ABR in time " + timepoint);
			method.clearAT(singlepathset);
			method.clearAT(nodesSet);
			System.out.println("End of time " + timepoint);
			System.out
					.println("-------------------------------------------------------------------");
			timepoint++;
			if (FBR.size() == 0 && VBR_RT.size() == 0 && VBR.size() == 0
					&& ABR.size() == 0) {
				System.out.println("SDN transfer complete!!");
				System.out.println("It totally cost " + timepoint + " seconds");
				break;
			}
		}
	}

}
