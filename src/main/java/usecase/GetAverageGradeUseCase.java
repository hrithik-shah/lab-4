package usecase;

import java.util.List;

import api.GradeDataBase;
import entity.Grade;
import entity.Team;

/**
 * GetAverageGradeUseCase class.
 */
public final class GetAverageGradeUseCase {
    private final GradeDataBase gradeDataBase;

    public GetAverageGradeUseCase(GradeDataBase gradeDataBase) {
        this.gradeDataBase = gradeDataBase;
    }

    /**
     * Get the average grade for a course across your team.
     * @param course The course.
     * @return The average grade.
     */
    public float getAverageGrade(String course) {
        // Call the API to get usernames of all your team members
        float sum = 0;
        int count = 0;

        final Team team = gradeDataBase.getMyTeam();
        List<String> members = List.of(team.getMembers());

        // Call the API to get all the grades for the course for all your team members
        for (String memberUsername : members) {
            try {
                // Get the grade of the team member for the specified course
                Grade grade = gradeDataBase.getGrade(memberUsername, course);

                if (grade != null) {
                    sum += grade.getGrade();
                    count++;
                }
            }
            catch (Exception ex) {
                // Handle the case where no grade is found or an error occurs
                System.out.println("Error retrieving grade for " + memberUsername + ": " + ex.getMessage());
            }
        }

        if (count == 0) {
            return 0;
        }
        return sum / count;
    }
}
