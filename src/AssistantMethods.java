import java.util.ArrayList;

public class AssistantMethods {
	public void combineAT(ArrayList<AvailableTransmission> a,
			ArrayList<AvailableTransmission> b) {
		if (b.size() != 0) {
			for (int i = 0; i < b.size(); i++) {
				a.add(b.get(i));
			}
		}
	}

	public void clearAT(ArrayList a) {
		if (a.size() != 0) {
			for (int i = 0; i < a.size(); i++) {
				a.remove(i);
			}
		}
	}

	public void restoreAT(AvailableTransmission[] a, int b) {
		for (int i = 0; i < b; i++) {
			a[i].CurrentCapacity = a[i].Capacity_;
			a[i].CurrentCapacityREAL = a[i].Capacity_;
		}
	}

	public void restoreATPART(AvailableTransmission[] a, int b) {
		for (int i = 0; i < b; i++) {
			a[i].CurrentCapacity = a[i].CurrentCapacityREAL;
		}
	}

	public ArrayList<AvailableTransmission> Findway(String start,
			String finish, AvailableTransmission[] w, int s, ReferenceForPass a) {
		ArrayList<AvailableTransmission> temp_ = new ArrayList<AvailableTransmission>();
		ArrayList<AvailableTransmission> theWayWePass = new ArrayList<AvailableTransmission>();
		ArrayList<String> theNodeWePass = new ArrayList<String>();
		theNodeWePass.add(start);
		for (int i = 0; i < s; i++) {
			temp_.add(w[i]);
		}
		double edge = Double.MAX_VALUE;
		String node = "";
		int count;
		int trigger = 0;
		boolean succeed = true;
		while (theNodeWePass.get(theNodeWePass.size() - 1).equals(finish) != true) {
			count = 0;
			trigger++;
			for (int i = 0; i < temp_.size(); i++) {
				if (temp_.get(i).CurrentCapacity != 0
						&& (temp_.get(i).Source.equals(theNodeWePass
								.get(theNodeWePass.size() - 1)) == true || temp_
								.get(i).Destination.equals(theNodeWePass
								.get(theNodeWePass.size() - 1)) == true)) {
					count = 1;
					if (temp_.get(i).Source.equals(theNodeWePass
							.get(theNodeWePass.size() - 1)) == true)
						node = temp_.get(i).Destination;
					else if (temp_.get(i).Destination.equals(theNodeWePass
							.get(theNodeWePass.size() - 1)) == true) {
						node = temp_.get(i).Source;
					}
					if (edge >= temp_.get(i).CurrentCapacity)
						edge = temp_.get(i).CurrentCapacity;
					theWayWePass.add(temp_.get(i));
					theNodeWePass.add(node);
					temp_.remove(i);
				}
			}
			if (count == 0 && theWayWePass.size() != 0) {
				temp_.add(theWayWePass.get((theWayWePass.size() - 1)));
				theWayWePass.remove(theWayWePass.size() - 1);
				theNodeWePass.remove(theNodeWePass.size() - 1);
			}
			if (trigger > s * s) {
				succeed = false;
				break;
			}
		}
		if (succeed == true) {
			for (int j = 0; j < theWayWePass.size(); j++) {
				theWayWePass.get(j).CurrentCapacity -= edge;
			}
			a.doublePass = edge;
			a.NodesetPass = theNodeWePass;
			return theWayWePass;
		} else
			return null;
	}
}
